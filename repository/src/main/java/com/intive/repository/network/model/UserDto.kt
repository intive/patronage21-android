package com.intive.repository.network.model

data class UserDto(
    var firstName: String,
    var lastName: String,
    var userName: String,
    var email: String,
    var phoneNumber: String,
    var gitHubUrl: String,
    var role: String,
    var status: String
)