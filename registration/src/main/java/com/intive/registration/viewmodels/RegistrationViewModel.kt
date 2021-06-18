package com.intive.registration.viewmodels

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intive.repository.Repository
import com.intive.repository.database.EventLogger
import com.intive.repository.domain.model.TechnologyGroup
import com.intive.repository.domain.model.UserRegistration
import com.intive.repository.network.response.RegistrationResponse
import com.intive.repository.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RegistrationViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
    private val eventLogger: EventLogger
) : ViewModel() {

    private val _availableTechnologies: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val availableTechnologies: State<Resource<List<String>>> = _availableTechnologies

    init {
        viewModelScope.launch(dispatchers.io) {
            _availableTechnologies.value = try {
                val response = repository.getTechnologies()
                withContext(dispatchers.main) {
                    Resource.Success(response)
                }
            } catch (ex: Exception) {
                withContext(dispatchers.main) {
                    Resource.Error(ex.localizedMessage)
                }
            }
        }
    }

    private val _title = MutableLiveData("")
    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName
    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> = _phoneNumber
    private val _login = MutableLiveData("")
    val login: LiveData<String> = _login
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword
    private val _githubUrl = MutableLiveData("")
    val githubUrl: LiveData<String> = _githubUrl
    private val _technologiesList = mutableListOf<String>()
    private val _rodoAgree = MutableLiveData(false)
    val rodoAgree: LiveData<Boolean> = _rodoAgree
    private val _regulationsAgree = MutableLiveData(false)
    val regulationsAgree: LiveData<Boolean> = _regulationsAgree

    fun onTitleChange(newValue: String) {
        _title.value = newValue
    }

    fun onFirstNameChange(newValue: String) {
        _firstName.value = newValue
    }

    fun onLastNameChange(newValue: String) {
        _lastName.value = newValue
    }

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun onPhoneNumberChange(newValue: String) {
        _phoneNumber.value = newValue
    }

    fun onLoginChange(newValue: String) {
        _login.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        _confirmPassword.value = newValue
    }

    fun onGithubUrlChange(newValue: String) {
        _githubUrl.value = newValue
    }

    fun onRodoAgreeChange(newValue: Boolean) {
        _rodoAgree.value = newValue
    }

    fun onRegulationsAgreeChange(newValue: Boolean) {
        _regulationsAgree.value = newValue
    }

    fun isFirstNameValid(): Boolean = firstName.value?.matches(Regex("\\w{2,30}")) ?: false
    fun isLastNameValid(): Boolean = lastName.value?.matches(Regex("\\w{2,30}")) ?: false
    fun isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()
    fun isPhoneNumberValid(): Boolean = phoneNumber.value?.matches(Regex("\\d{9,9}")) ?: false

    fun isPasswordValid(): Boolean = password.value?.let {
        it.length in 8..20 &&
                it.contains(Regex("[A-Z]+")) &&
                it.contains(Regex("[a-z]+")) &&
                it.contains(Regex("[0-9]+")) &&
                it.contains(Regex("[!@#\\$%\\^&\\*()\\-\\+]+"))
    } ?: false

    fun isConfirmPasswordValid(): Boolean =
        isPasswordValid() && password.value == confirmPassword.value

    fun isTechnologiesListValid(): Boolean =
        !_technologiesList.isNullOrEmpty() && _technologiesList.size < 4

    fun isLoginValid(): Boolean = login.value?.matches(Regex("[A-Za-z0-9]{2,15}")) ?: false
    fun isGithubUrlValid(): Boolean =
        githubUrl.value?.let {
            it.isEmpty() ||
                    it.matches(Regex("(https?:\\/\\/)?(www\\.)?github.com\\/[\\-a-zA-Z0-9]{1,39}")) &&
                    !it.startsWith("-") &&
                    !it.endsWith("-") &&
                    !it.contains("--")
        } ?: true

    fun isFormValid(): Boolean = isFirstNameValid() &&
            isLastNameValid() &&
            isEmailValid() &&
            isPhoneNumberValid() &&
            isTechnologiesListValid() &&
            isLoginValid() &&
            isPasswordValid() &&
            isConfirmPasswordValid() &&
            isGithubUrlValid() &&
            rodoAgree.value!! &&
            regulationsAgree.value!!

    fun updateTechnologies(technology: String) {
        if (_technologiesList.contains(technology)) {
            _technologiesList.remove(technology)
        } else {
            _technologiesList.add(technology)
        }
    }

    private val _responseState: MutableState<Resource<String>?> = mutableStateOf(null)
    val responseState: State<Resource<String>?> = _responseState

    val _scrollUp: MutableState<Boolean> = mutableStateOf(false)
    val scrollUp: State<Boolean> = _scrollUp

    fun sendDataToServer() {
        viewModelScope.launch(dispatchers.io) {
            _responseState.value = Resource.Loading()
            val user = UserRegistration(
                gender = if(_title.value.isNullOrEmpty()) "MALE" else _title.value!!,
                firstName = firstName.value!!,
                lastName = lastName.value!!,
                email = email.value!!,
                phoneNumber = phoneNumber.value!!,
                groups = _technologiesList.map { TechnologyGroup(it) },
                login = login.value!!,
                gitHubUrl = githubUrl.value!!
            )
            val receivedResponse: Response<RegistrationResponse>
            try {
                receivedResponse = repository.sendDataFromRegistrationForm(user)
                if (receivedResponse.isSuccessful) {
                    eventLogger.log("Udana rejestracja", login.value!!)
                    _responseState.value = Resource.Success("")
                } else {
                    _scrollUp.value = true
                    val gson = Gson()
                    val type = object : TypeToken<RegistrationResponse>() {}.type
                    var errorResponse: RegistrationResponse? = gson.fromJson(receivedResponse.errorBody()!!.charStream(), type)
                    var message = ""
                    for(error in errorResponse!!.violationErrors!!) {
                        message += error.fieldName + "\n" + error.message + "\n\n"
                    }
                    val responseCode = receivedResponse.code()
                    _responseState.value = when {
                        isServerError(responseCode) -> Resource.Error(SERVER_ERROR)
                        responseCode == RESPONSE_NOT_FOUND -> Resource.Error(RESPONSE_NOT_FOUND.toString())
                        else -> Resource.Error(message = message)
                    }
                    eventLogger.log("Nie udana rejestracja", login.value!!)
                }
            } catch (ex: Exception) {
                _responseState.value = Resource.Error(ex.localizedMessage)
            }
        }
    }

    fun resetScrolling() {
        _scrollUp.value = false
    }

    fun resetResponseState() {
        _responseState.value = null
    }

}