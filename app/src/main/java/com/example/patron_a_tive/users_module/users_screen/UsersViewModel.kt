package com.example.patron_a_tive.users_module.users_screen

import androidx.lifecycle.ViewModel

class UsersViewModel : ViewModel() {
    val users = listOf(
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski")
    )
}