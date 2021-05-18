package com.intive.users.presentation.deactivate_user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.RESPONSE_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DeactivateUserViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    val typedLastName = mutableStateOf("")
    private val userLastName = mutableStateOf<String?>(null)

    private val deactivateUserChannel = Channel<DeactivateUserEvent>()
    val deactivateUserEvent = deactivateUserChannel.receiveAsFlow()

    private var login: String? = null

    init {
        login = repository.getUserLoginOrNull()
        login?.let { getUser(it) }
    }

    fun onValueChange(newValue: String) {
        typedLastName.value = newValue
    }

    fun onConfirmClick() {
        viewModelScope.launch(dispatchers.io) {
            try {
                val response = repository.deactivateUser(login!!)
                if(response.isSuccessful) {
                    deactivateUserChannel.send(DeactivateUserEvent.NavigateToRegistrationScreen)
                    repository.logoutUser()
                } else {
                    deactivateUserChannel.send(DeactivateUserEvent.ShowErrorMessage)
                }
            } catch (e: Exception) {
                deactivateUserChannel.send(DeactivateUserEvent.ShowErrorMessage)
            }
        }
    }

    private fun getUser(login: String) {
        viewModelScope.launch(dispatchers.io) {
            val user = repository.getUser(login)
            userLastName.value = user.lastName
        }
    }

    fun isLastNameCorrect(): Boolean = typedLastName.value == userLastName.value

    sealed class DeactivateUserEvent {
        object NavigateToRegistrationScreen : DeactivateUserEvent()
        object ShowErrorMessage : DeactivateUserEvent()
    }
}