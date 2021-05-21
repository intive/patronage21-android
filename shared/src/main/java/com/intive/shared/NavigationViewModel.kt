package com.intive.shared

import android.util.Log
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

    init {
        viewModelScope.launch {
            if(repository.isUserLogged()){
                _loggedInChannel.send(LoginEvent.UserLoggedIn)
            } else {
                _loggedInChannel.send(LoginEvent.UserLoggedOut)
            }
        }
    }

    fun loginUser(login: String){
        repository.loginUser(login)
        viewModelScope.launch {
            _loggedInChannel.send(LoginEvent.UserLoggedIn)
        }
    }

    fun logoutUser() {
        repository.logoutUser()
        viewModelScope.launch {
            _loggedInChannel.send(LoginEvent.UserLoggedOut)
        }
    }

    sealed class LoginEvent {
        object UserLoggedIn : LoginEvent()
        object UserLoggedOut : LoginEvent()
    }
}