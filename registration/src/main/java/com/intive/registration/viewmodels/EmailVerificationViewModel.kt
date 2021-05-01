package com.intive.registration.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.intive.repository.Repository
import com.intive.repository.util.Resource
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

    private val _responseState: MutableState<Resource<String>?> = mutableStateOf(null)
    val responseState: State<Resource<String>?> = _responseState
    fun sendCodeToServer() {
        viewModelScope.launch {
            _responseState.value = Resource.Loading()
            val response : Response<String>
            try {
                response = repository.sendCodeToServer(code.value!!, email)
                if(response.isSuccessful) {
                    _responseState.value = Resource.Success("")
                }
                else {
                    _responseState.value = Resource.Error(response.message())
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