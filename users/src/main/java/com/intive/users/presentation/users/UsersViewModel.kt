package com.intive.users.presentation.users

import androidx.lifecycle.ViewModel
import com.intive.users.domain.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}