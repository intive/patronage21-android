package com.intive.repository.domain.model

data class UserRegistration(
    val gender: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val technologies: String,
    val login: String,
    val password: String,
    val githubUrl: String,
)