package com.intive.registration.viewmodels

import androidx.lifecycle.*
import com.intive.repository.local.LocalRepository

class LoginViewModel(
    val repo: LocalRepository
) : ViewModel() {

    private val _login = MutableLiveData("")
    val login: LiveData<String> = _login
    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password


    fun onLoginChange(newValue: String) {
        _login.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

}
