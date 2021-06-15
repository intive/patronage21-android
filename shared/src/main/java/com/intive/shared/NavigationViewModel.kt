package com.intive.shared

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.local.LocalRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _loggedInChannel = Channel<LoginEvent>()
    val loginFlow = _loggedInChannel.receiveAsFlow()

    private val _loggedState: MutableState<LoginEvent> = mutableStateOf(LoginEvent.UserLoggedIn)
    val loggedState: State<LoginEvent> = _loggedState

    init {
        viewModelScope.launch {
            if(repository.isUserLogged()){
                _loggedInChannel.send(LoginEvent.UserLoggedIn)
                _loggedState.value = LoginEvent.UserLoggedIn
            } else {
                _loggedInChannel.send(LoginEvent.UserLoggedOut)
                _loggedState.value = LoginEvent.UserLoggedOut
            }
        }
    }

    fun loginUser(login: String){
        repository.loginUser(login)
        viewModelScope.launch {
            _loggedInChannel.send(LoginEvent.UserLoggedIn)
            _loggedState.value = LoginEvent.UserLoggedIn
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            _loggedInChannel.send(LoginEvent.UserLoggedOut)
            _loggedState.value = LoginEvent.UserLoggedOut
        }
    }

    sealed class LoginEvent {
        object UserLoggedIn : LoginEvent()
        object UserLoggedOut : LoginEvent()
    }
}