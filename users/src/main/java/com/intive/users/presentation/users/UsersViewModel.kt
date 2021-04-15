package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.intive.users.domain.User

class UsersViewModel : ViewModel() {
    val users = List(5) {
        User(
            firstName = "Jan",
            lastName = "Kowalski",
            gender = "Mężczyzna",
            email = "jankowalski@gmal.com",
            phoneNumber = "123456789",
            github = "github.com/KowalskiJan",
            bio = "Jestem programista"
        )
    }

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}