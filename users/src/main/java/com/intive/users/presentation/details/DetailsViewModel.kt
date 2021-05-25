package com.intive.users.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.domain.model.User
import com.intive.repository.local.LocalRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel() : ViewModel() {
    data class Project(val name: String, val role: String)

    private val userContactEventChannel = Channel<UserContactEvent>()
    val userContactEvent = userContactEventChannel.receiveAsFlow()

    val user = User(
        firstName = "Jan",
        lastName = "Kowalski",
        login = "jkowalski",
        gender = "Mężczyzna",
        email = "jankowalski@gmal.com",
        phoneNumber = "123456789",
        github = "github.com/KowalskiJan",
        bio = "Jestem programista",
        role = "Candidate"
    )

    val projects = listOf(
        Project("Projekt I", "Scrum Master"),
        Project("Projekt II", "Product Owner"),
        Project("Projekt III", "Developer")
    )

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
