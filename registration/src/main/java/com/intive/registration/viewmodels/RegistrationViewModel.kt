package com.intive.registration.viewmodels

import android.util.Patterns
import androidx.lifecycle.*
import com.intive.registration.R
import com.intive.registration.util.RegistrationFormState
import com.intive.repository.Repository
import com.intive.repository.domain.model.UserRegistration
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Collections.emptyList

class RegistrationViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _registrationFormState =
        MutableLiveData<RegistrationFormState>(RegistrationFormState.Ok)
    val registrationFormState: LiveData<RegistrationFormState> = _registrationFormState

    var availableTechnologies: List<String> = emptyList()

    init {
        viewModelScope.launch {
            _registrationFormState.value = RegistrationFormState.Downloading
            try {
                availableTechnologies = repository.getTechnologyGroups()
            } catch (ex: Exception) {
                _registrationFormState.value =
                    RegistrationFormState.Error(R.string.internet_connection_error)
            }
            if (availableTechnologies.isEmpty()) {
                _registrationFormState.value =
                    RegistrationFormState.Error(R.string.internet_connection_error)
            } else {
                _registrationFormState.value = RegistrationFormState.Ok
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

    fun isFirstNameValid(): Boolean = firstName.value?.length ?: 0 >= 3
    fun isLastNameValid(): Boolean = lastName.value?.length ?: 0 >= 3
    fun isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()
    fun isPhoneNumberValid(): Boolean = phoneNumber.value?.matches(Regex("\\d{9,9}")) ?: false
    fun isPasswordValid(): Boolean = password.value?.length ?: 0 >= 8
    fun isConfirmPasswordValid(): Boolean = password.value == confirmPassword.value
    fun isTechnologiesListValid(): Boolean =
        !_technologiesList.isNullOrEmpty() && _technologiesList.size < 4

    fun isLoginValid(): Boolean = login.value?.length ?: 0 >= 4 && isLoginAvailable()
    fun isGithubUrlValid(): Boolean =
        githubUrl.value.isNullOrEmpty() || githubUrl.value!!.matches(Regex("(https?:\\/\\/)?(www\\.)?github.com\\/[-a-zA-Z0-9]{1,39}"))

    private fun isLoginAvailable(): Boolean {
        return true
    }

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

    private val _responseState = MutableLiveData<ResponseState>(ResponseState.Waiting)
    val responseState: LiveData<ResponseState> = _responseState

    fun sendDataToServer() {
        viewModelScope.launch {
            _registrationFormState.value = RegistrationFormState.Sending
            _responseState.value = ResponseState.Waiting
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
            val receivedResponse : Response<String>
            try {
                receivedResponse = repository.sendDataFromRegistrationForm(user)
                if(receivedResponse.isSuccessful) {
                    _responseState.value = ResponseState.Ok
                }
                else {
                    _responseState.value = ResponseState.Error(receivedResponse.message())
                }
            } catch (ex: Exception) {
                _responseState.value = ResponseState.Error(ex.message!!)
            }
        }
    }

    fun resetResponseState() {
        _responseState.value = ResponseState.Waiting
        resetFormState()
    }

    fun resetFormState() {
        _registrationFormState.value = RegistrationFormState.Ok
    }

}

sealed class ResponseState {
    object Waiting: ResponseState()
    object Ok : ResponseState()
    data class Error(val message: String) : ResponseState()
}