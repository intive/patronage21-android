package com.intive.registration.viewmodels

import androidx.lifecycle.*

class EmailVerificationViewModel : ViewModel() {

    private val _code = MutableLiveData("")
    val code: LiveData<String> = _code
    lateinit var email: String

     fun onCodeChange(newValue: String) {
        _code.value = newValue
    }

    fun isCodeValid(): Boolean = code.value?.matches(Regex("\\d{8,8}")) ?: false
    fun isCodeCorrect(): Boolean = code.value.toString().toLong()> 55555555L //repository.isCodeCorrect(code.value)
}
