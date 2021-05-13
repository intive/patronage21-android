package com.intive.registration.viewmodels

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.UserRegistration
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RegistrationViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _availableTechnologies: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val availableTechnologies: State<Resource<List<String>>> = _availableTechnologies

    init {
        viewModelScope.launch(dispatchers.io) {
            _availableTechnologies.value = try {
                val response = repository.getTechnologies()
                Resource.Success(response)
            } catch (ex: Exception) {
                Resource.Error(ex.localizedMessage)
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

    fun isFirstNameValid(): Boolean = firstName.value?.matches(Regex("[A-Za-z]{3,20}")) ?: false
    fun isLastNameValid(): Boolean = lastName.value?.matches(Regex("[A-Za-z]{2,20}")) ?: false
    fun isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()
    fun isPhoneNumberValid(): Boolean = phoneNumber.value?.matches(Regex("\\d{9,9}")) ?: false

    fun isPasswordValid(): Boolean = password.value?.let {
        it.length >= 8 &&
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
        githubUrl.value?.let{
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

    fun sendDataToServer() {
        viewModelScope.launch(dispatchers.io) {
            _responseState.value = Resource.Loading()
            val user = UserRegistration(
                gender = _title.value!!,
                firstName = firstName.value!!,
                lastName = lastName.value!!,
                email = email.value!!,
                phoneNumber = phoneNumber.value!!,
                technologies = _technologiesList.toString(),
                login = login.value!!,
                password = password.value!!, //hash??
                githubUrl = githubUrl.value!!
            )
            for (item in _technologiesList) {
                println(item)
            }
            println(rodoAgree.value)
            println(_regulationsAgree.value)
            val receivedResponse: Response<String>
            try {
                receivedResponse = repository.sendDataFromRegistrationForm(user)
                if (receivedResponse.isSuccessful) {
                    _responseState.value = Resource.Success("")
                } else {
                    _responseState.value = Resource.Error(receivedResponse.message())
                }
            } catch (ex: Exception) {
                _responseState.value = Resource.Error(ex.localizedMessage)
            }
        }
    }

    fun resetResponseState() {
        _responseState.value = null
    }

}