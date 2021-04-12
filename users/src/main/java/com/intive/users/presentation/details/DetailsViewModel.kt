package com.intive.users.presentation.details

import androidx.lifecycle.ViewModel
import com.intive.users.domain.User
import com.intive.users.repository.remote.model.UserDTO

class DetailsViewModel : ViewModel(){
    data class Project(val name: String, val role: String)

    val user = User(
        "Mężczyzna",
        "Jan",
        "Kowalski",
        "jankowalski@gmal.com",
        "123456789",
        "github.com/KowalskiJan",
        "Jestem programista",
    )

    val projects = listOf(
        Project("Projekt I", "Scrum Master"),
        Project("Projekt II", "Product Owner"),
        Project("Projekt III", "Developer")
    )
}
