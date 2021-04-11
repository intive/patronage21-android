package com.intive.users.presentation.users

import androidx.lifecycle.ViewModel
import com.intive.users.repository.remote.model.UserDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsersViewModel : ViewModel() {
    val users = List(5) {
        UserDTO(
            "Mężczyzna",
            "Jan",
            "Kowalski",
            "jankowalski@gmal.com",
            "123456789",
            "github.com/KowalskiJan",
            "Jestem programista"
        )
    }

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}