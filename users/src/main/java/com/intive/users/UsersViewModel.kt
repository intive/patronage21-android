package com.intive.users

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsersViewModel : ViewModel() {
    val users = List(5) {
        Person(
            Gender.MALE,
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