package com.intive.registration.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.intive.repository.Repository
import com.intive.repository.util.*
import kotlinx.coroutines.launch
import retrofit2.Response

class EmailVerificationViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _code = MutableLiveData("")
    val code: LiveData<String> = _code
    lateinit var email: String
    lateinit var login: String

    fun onCodeChange(newValue: String) {
        _code.value = newValue
    }

    fun isCodeValid(): Boolean = code.value?.matches(Regex("\\d{8,8}")) ?: false

    private val _responseState: MutableState<Resource<String>?> = mutableStateOf(null)
    val responseState: State<Resource<String>?> = _responseState
    fun sendCodeToServer() {
        viewModelScope.launch(dispatchers.io) {
            _responseState.value = Resource.Loading()
            val response : Response<String>
            try {
                response = repository.sendCodeToServer(code.value!!, email)
                if(response.isSuccessful) {
                    _responseState.value = Resource.Success("")
                    repository.loginUser(login)
                }
                else {
                    val responseCode = response.code()
                    _responseState.value = when {
                        isServerError(responseCode) -> Resource.Error(SERVER_ERROR)
                        responseCode == RESPONSE_NOT_FOUND -> Resource.Error(RESPONSE_NOT_FOUND.toString())
                        else -> Resource.Error(response.message())
                    }
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