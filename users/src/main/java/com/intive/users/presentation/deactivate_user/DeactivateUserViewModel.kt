package com.intive.users.presentation.deactivate_user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DeactivateUserViewModel : ViewModel() {

    val lastName = mutableStateOf("")
    val login = mutableStateOf("")

    fun onConfirmClick() {}

    fun onValueChange(newValue: String) {
        lastName.value = newValue
    }
}