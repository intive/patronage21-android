package com.intive.users.presentation.details

import androidx.lifecycle.ViewModel
import com.intive.repository.domain.model.User

class DetailsViewModel : ViewModel(){
    data class Project(val name: String, val role: String)

    val user = User(
        firstName = "Jan",
        lastName = "Kowalski",
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
}
