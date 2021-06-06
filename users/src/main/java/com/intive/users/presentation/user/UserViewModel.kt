package com.intive.users.presentation.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
    private val userLogin: String,
) : ViewModel() {

    val typedLastName = mutableStateOf("")
    private val userLastName = mutableStateOf<String?>(null)
    val shouldShowDeactivationSuccessfulDialog = mutableStateOf(false)

    private val _user: MutableState<Resource<User>> = mutableStateOf(Resource.Loading())
    val user: State<Resource<User>> = _user

    private val deactivateUserChannel = Channel<DeactivateUserEvent>()
    val deactivateUserEvent = deactivateUserChannel.receiveAsFlow()

    private val userContactEventChannel = Channel<UserContactEvent>()
    val userContactEvent = userContactEventChannel.receiveAsFlow()

    init {
        try {
            viewModelScope.launch(dispatchers.io) {
                val user = repository.getUser(userLogin)
                _user.value = Resource.Success(user)
                userLastName.value = user.lastName
            }
        } catch (e: Exception) {
            _user.value = Resource.Error(e.localizedMessage)
        }
    }

    fun onValueChange(newValue: String) {
        typedLastName.value = newValue
    }

    fun onConfirmClick() {
        viewModelScope.launch(dispatchers.io) {
            try {
                val response = repository.deactivateUser(userLogin)
                if (response.isSuccessful) {
                    repository.logoutUser()
                    deactivateUserChannel.send(DeactivateUserEvent.ShowSuccessMessage)
                } else {
                    deactivateUserChannel.send(DeactivateUserEvent.ShowErrorMessage)
                }
            } catch (e: Exception) {
                deactivateUserChannel.send(DeactivateUserEvent.ShowErrorMessage)
            }
        }
    }

    fun onDialogDismiss() = viewModelScope.launch {
        deactivateUserChannel.send(DeactivateUserEvent.NavigateToRegistrationScreen)
    }

    fun isLastNameCorrect(): Boolean = typedLastName.value == userLastName.value

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

    sealed class DeactivateUserEvent {
        object NavigateToRegistrationScreen : DeactivateUserEvent()
        object ShowSuccessMessage : DeactivateUserEvent()
        object ShowErrorMessage : DeactivateUserEvent()
    }
}