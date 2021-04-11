package com.intive.users.domain

data class User(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val github: String,
    val bio: String
)
