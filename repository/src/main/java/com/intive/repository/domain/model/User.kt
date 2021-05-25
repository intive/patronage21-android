package com.intive.repository.domain.model

data class User(
    var firstName: String,
    var lastName: String,
    var login: String,
    var gender: String,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String,
    var role: String,
)
