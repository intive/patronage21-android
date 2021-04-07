package com.intive.users

import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel(){
    data class Project(val name: String, val role: String)

    val user = Person(
        "Jan",
        "Kowalski",
        "jankowalski@gmal.com",
        "123456789",
        "github.com/KowalskiJan",
        "Jestem programista"
    )

    val projects = listOf(
        Project("Projekt I", "Scrum Master"),
        Project("Projekt II", "Product Owner"),
        Project("Projekt III", "Developer")
    )
}
