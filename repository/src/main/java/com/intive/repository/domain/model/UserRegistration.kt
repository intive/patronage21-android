package com.intive.repository.domain.model

data class UserRegistration(
    val firstName: String,
    val login: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val gitHubUrl: String,
    val gender: String,
    val groups: List<TechnologyGroup>,
)

data class TechnologyGroup(val name: String)