package com.intive.registration.viewmodels

import androidx.lifecycle.*

class LoginViewModel() : ViewModel() {

    private val _login = MutableLiveData<String>("")
    val login: LiveData<String> = _login
    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    fun onLoginChange(newValue: String) {
        _login.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

}
