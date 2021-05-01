package com.intive.registration.viewmodels

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.intive.repository.Repository
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NoCodeViewModel(
    private val repository: Repository
) : ViewModel() {

    lateinit var firstEmail: String //email entered in first screen

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

     fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()

    fun sendRequestForCode() {
        viewModelScope.launch {
            try {
                repository.sendRequestForCode(email.value!!)
            } catch (ex: Exception) { }
        }
    }
}
