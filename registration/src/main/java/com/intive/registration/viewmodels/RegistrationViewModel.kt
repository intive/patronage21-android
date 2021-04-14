package com.intive.registration.viewmodels

import android.util.Patterns
import androidx.lifecycle.*

class RegistrationViewModel : ViewModel() {

    val availableTechnologies = listOf("Java", "JavaScript", "QA", "Mobile (Android)")

    private val _title = MutableLiveData("Pan")
    val title: LiveData<String> = _title
    private val _firstName = MutableLiveData("")
    val firstName: LiveData<String> = _firstName
    private val _lastName = MutableLiveData("")
    val lastName: LiveData<String> = _lastName
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _phoneNumber = MutableLiveData("")
    val phoneNumber:LiveData<String> = _phoneNumber
    private val _login = MutableLiveData("")
    val login: LiveData<String> = _login
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword
    private val _githubUrl = MutableLiveData("")
    val githubUrl: LiveData<String> = _githubUrl
    private val _technologies = MutableLiveData<List<String>>(emptyList())
    private val technologies: LiveData<List<String>> = _technologies
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

    fun validateFirstName(): Boolean = firstName.value?.length ?: 0 >= 3
    fun validateLastName(): Boolean = lastName.value?.length ?: 0 >= 3
    fun validateEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()
    fun validatePhoneNumber(): Boolean = phoneNumber.value?.matches(Regex("\\d{9,9}")) ?: false
    fun validatePassword(): Boolean = password.value?.length ?: 0 >= 8
    fun validateConfirmPassword(): Boolean = password.value == confirmPassword.value
    fun validateTechnologies(): Boolean =
        !technologies.value.isNullOrEmpty() && technologies.value!!.size < 4

    fun validateLogin(): Boolean = login.value?.length ?: 0 >= 4 && isLoginAvailable()
    fun validateGithubUrl(): Boolean =
        githubUrl.value.isNullOrEmpty() || githubUrl.value!!.matches(Regex("(https?:\\/\\/)?(www\\.)?github.com\\/[-a-zA-Z0-9]{1,39}"))

    private fun isLoginAvailable(): Boolean {
        return true
    }

    fun validateForm(): Boolean = validateFirstName() &&
            validateLastName() &&
            validateEmail() &&
            validatePhoneNumber() &&
            validateTechnologies() &&
            validateLogin() &&
            validatePassword() &&
            validateConfirmPassword() &&
            validateGithubUrl() &&
            rodoAgree.value!! &&
            regulationsAgree.value!!

//    fun isFormValid(): LiveData<Boolean> {
//        val validatorResult = MediatorLiveData<Boolean>()
//        validatorResult.addSource(firstName) { validatorResult.value = validateForm() }
//        validatorResult.addSource(lastName) { validatorResult.value = validateForm() }
//        validatorResult.addSource(email) { validatorResult.value = validateForm() }
//        validatorResult.addSource(phoneNumber) { validatorResult.value = validateForm() }
//        validatorResult.addSource(login) { validatorResult.value = validateForm() }
//        validatorResult.addSource(password) { validatorResult.value = validateForm() }
//        validatorResult.addSource(confirmPassword) { validatorResult.value = validateForm() }
//        validatorResult.addSource(githubUrl) { validatorResult.value = validateForm() }
//        validatorResult.addSource(technologies) { validatorResult.value = validateForm() }
//        validatorResult.addSource(rodoAgree) { validatorResult.value = validateForm() }
//        validatorResult.addSource(regulationsAgree) { validatorResult.value = validateForm() }
//
//
//        return validatorResult
//    }

    fun updateTechnologies(technology: String) {
        if (_technologiesList.contains(technology)) {
            _technologiesList.remove(technology)
        } else {
            _technologiesList.add(technology)
        }
        _technologies.value = _technologiesList
    }

}