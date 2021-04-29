package com.intive.registration.viewmodels

import androidx.lifecycle.*
import com.intive.registration.R
import com.intive.registration.util.RegistrationFormState
import com.intive.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class EmailVerificationViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _code = MutableLiveData("")
    val code: LiveData<String> = _code
    lateinit var email: String

    fun onCodeChange(newValue: String) {
        _code.value = newValue
    }

    fun isCodeValid(): Boolean = code.value?.matches(Regex("\\d{8,8}")) ?: false

    private val _formState = MutableLiveData<RegistrationFormState>(RegistrationFormState.Ok)
    val formState: LiveData<RegistrationFormState> = _formState

    private val _isCodeCorrect = MutableLiveData<CodeState>(CodeState.NotSent)
    val isCodeCorrect: LiveData<CodeState> = _isCodeCorrect
    fun sendCodeToServer() {
        viewModelScope.launch {
            _formState.value = RegistrationFormState.Sending
            val response: Response<String>
            try {
                response = repository.sendCodeToServer(code.value!!, email)
                _formState.value = RegistrationFormState.Ok
                if (response.isSuccessful) {
                    _isCodeCorrect.value = CodeState.Correct
                }
                else {
                    _isCodeCorrect.value = CodeState.InCorrect
                    println(response.code())
                    println(response.message())
                    println(response.body())
                }
            } catch (ex: Exception) {
                println("exception ${ex.message}")
                _formState.value = RegistrationFormState.Error(R.string.internet_connection_error)
            }
        }
    }
}

sealed class CodeState {
    object Correct : CodeState()
    object InCorrect : CodeState()
    object NotSent : CodeState()
}
