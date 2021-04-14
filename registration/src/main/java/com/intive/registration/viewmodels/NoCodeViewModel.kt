package com.intive.registration.viewmodels

import android.util.Patterns
import androidx.lifecycle.*

class NoCodeViewModel : ViewModel() {

    lateinit var firstEmail: String //email entered in first screen

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

     fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun validateEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()
}
