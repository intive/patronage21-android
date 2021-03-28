package com.intive.users

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