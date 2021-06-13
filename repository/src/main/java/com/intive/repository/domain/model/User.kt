package com.intive.repository.domain.model

data class User(
    var login: String,
    var firstName: String,
    var lastName: String,
    var gender: String,
    var image: String?,
    var projects: List<Project>,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String,
    var role: String,
)
