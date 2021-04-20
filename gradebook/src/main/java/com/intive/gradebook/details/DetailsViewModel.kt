package com.intive.gradebook.details

import androidx.lifecycle.ViewModel
import com.intive.gradebook.domain.Grade
import com.intive.gradebook.domain.Person

class DetailsViewModel : ViewModel() {
    val user = Person("Jan", "Kowalski")
    val grades = listOf(
        Grade(
            "Etap I",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            4.0
        ),
        Grade(
            "Etap II",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            5.0
        ),
        Grade(
            "Etap III",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            4.0
        ),
        Grade(
            "Etap IV",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            3.0
        ),
        Grade(
            "Etap V",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            5.0
        )
    )
}
