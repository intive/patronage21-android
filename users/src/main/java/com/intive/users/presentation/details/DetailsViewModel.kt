package com.intive.users.presentation.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.Project
import com.intive.repository.domain.model.User
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val userLogin: String,
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val userContactEventChannel = Channel<UserContactEvent>()
    val userContactEvent = userContactEventChannel.receiveAsFlow()

    private val _user: MutableState<Resource<User>> = mutableStateOf(Resource.Loading())
    val user: State<Resource<User>> = _user

    init {
        try {
            viewModelScope.launch(dispatchers.io) {
                val user = repository.getUser(userLogin)
                _user.value = Resource.Success(user)
            }
        } catch (e: Exception) {
            _user.value = Resource.Error(e.localizedMessage)
        }
    }

    fun onDialPhoneClicked(phoneNumber: String) = viewModelScope.launch {
        userContactEventChannel.send(UserContactEvent.DialPhoneNumber(phoneNumber))
    }

    fun onSendEmailClicked(email: String) = viewModelScope.launch {
        userContactEventChannel.send(UserContactEvent.SendEmail(email))
    }

    fun onLaunchWebsiteClicked(websiteUrl: String) = viewModelScope.launch {
        userContactEventChannel.send(UserContactEvent.LaunchWebsite(websiteUrl))
    }

    sealed class UserContactEvent {
        data class DialPhoneNumber(val phoneNumber: String) : UserContactEvent()
        data class SendEmail(val email: String) : UserContactEvent()
        data class LaunchWebsite(val websiteUrl: String) : UserContactEvent()
    }
}
