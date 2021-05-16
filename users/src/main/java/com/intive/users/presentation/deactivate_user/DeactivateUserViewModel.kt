package com.intive.users.presentation.deactivate_user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.RESPONSE_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DeactivateUserViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    val lastName = mutableStateOf("")
    private var user: User = User(
        "#", "#", "#", "#",
        "#", "#", "#", "#", "#"
    )

    private val deactivateUserChannel = Channel<DeactivateUserEvent>()
    val deactivateUserEvent = deactivateUserChannel.receiveAsFlow()

    fun onValueChange(newValue: String) {
        lastName.value = newValue
    }

    fun onConfirmClick() {
        viewModelScope.launch(dispatchers.io) {
            try {
                val response = repository.deactivateUser(user.login).code()
                if(response == RESPONSE_OK) {
                    deactivateUserChannel.send(DeactivateUserEvent.NavigateToRegistrationScreen)
                } else {
                    deactivateUserChannel.send(DeactivateUserEvent.ShowErrorMessage)
                }
            } catch (e: Exception) {
                deactivateUserChannel.send(DeactivateUserEvent.ShowErrorMessage)
            }
        }
    }

    fun getUser(login: String?) {
        if (login == null)
            return

        viewModelScope.launch(dispatchers.io) {
            user = repository.getUser(login)
        }
    }

    fun isLastNameCorrect(): Boolean = lastName.value == user.lastName

    sealed class DeactivateUserEvent() {
        object NavigateToRegistrationScreen : DeactivateUserEvent()
        object ShowErrorMessage : DeactivateUserEvent()
    }
}