package com.intive.users.presentation.user

import android.util.Log
import android.util.Patterns
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

    private val editUserChannel = Channel<EditUserEvent>()
    val editUserEvent = editUserChannel.receiveAsFlow()

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

    fun onEditUserButtonPressed(user: User) {
        try {
            viewModelScope.launch(dispatchers.io) {
                val response = repository.updateUser(user)

                if(response.isSuccessful) {
                    editUserChannel.send(EditUserEvent.OnSuccessfulEdit)
                } else {
                    editUserChannel.send(EditUserEvent.OnFailedEdit)
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                editUserChannel.send(EditUserEvent.OnFailedEdit)
            }
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

    fun isFirstNameValid(firstName: String): Boolean = firstName.matches(Regex("[A-Za-z]{2,30}"))
    fun isLastNameValid(lastName: String): Boolean = lastName.matches(Regex("[A-Za-z]{2,30}"))
    fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isPhoneNumberValid(phoneNumber: String): Boolean = phoneNumber.matches(Regex("\\d{9,9}"))
    fun isGithubUrlValid(githubUrl: String): Boolean =
        githubUrl.let {
            it.isEmpty() ||
                    it.matches(Regex("(https?:\\/\\/)?(www\\.)?github.com\\/[\\-a-zA-Z0-9]{1,39}")) &&
                    !it.startsWith("-") &&
                    !it.endsWith("-") &&
                    !it.contains("--")
        }
    fun isBioValid(bio: String): Boolean = bio.isNotEmpty() && bio.length < 100

    fun isFormValid(
        user: User,
    ) = isFirstNameValid(user.firstName) &&
            isLastNameValid(user.lastName) &&
            isEmailValid(user.email) &&
            isPhoneNumberValid(user.phoneNumber) &&
            isGithubUrlValid(user.github) &&
            isBioValid(user.bio)

    fun isLastNameEnteredCorrectly(): Boolean = typedLastName.value == userLastName.value

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

    sealed class EditUserEvent {
        object OnSuccessfulEdit : EditUserEvent()
        object OnFailedEdit : EditUserEvent()
    }
}