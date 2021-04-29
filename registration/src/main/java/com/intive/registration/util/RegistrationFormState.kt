package com.intive.registration.util

sealed class RegistrationFormState {
    object Downloading : RegistrationFormState()
    object Sending : RegistrationFormState()
    object Ok : RegistrationFormState()
    data class Error(val messageResourceId: Int) : RegistrationFormState()
}