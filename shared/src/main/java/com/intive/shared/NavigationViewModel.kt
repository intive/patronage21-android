package com.intive.shared

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.local.LocalRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _loggedInChannel = Channel<LoginEvent>()
    val loginFlow = _loggedInChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            if(localRepository.isUserLogged()){
                _loggedInChannel.send(LoginEvent.UserLoggedIn)
            } else {
                _loggedInChannel.send(LoginEvent.UserLoggedOut)
            }
        }
    }

    fun loginUser(login: String){
        localRepository.loginUser(login)
        viewModelScope.launch {
            _loggedInChannel.send(LoginEvent.UserLoggedIn)
        }
    }

    fun logoutUser() {
        localRepository.logoutUser()
        viewModelScope.launch {
            _loggedInChannel.send(LoginEvent.UserLoggedOut)
        }
    }

    sealed class LoginEvent {
        object UserLoggedIn : LoginEvent()
        object UserLoggedOut : LoginEvent()
    }
}