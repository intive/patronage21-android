package com.intive.users.repository.remote.model

data class UserDTO(
    var firstName: String,
    var lastName: String,
    val nickname: String,
    var gender: String,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String
)