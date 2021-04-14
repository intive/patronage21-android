package com.intive.gradebook

import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.intive.gradebook.R

class DetailsViewModel : ViewModel(){
    data class Grade(val name: String, val grounds: String, val grade: Double)

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
        ))
}
