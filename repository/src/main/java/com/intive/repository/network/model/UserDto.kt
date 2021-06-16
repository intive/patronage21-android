package com.intive.repository.network.model

import com.intive.repository.domain.model.Project

data class UserDto(
    var login: String,
    var image: String?,
    var firstName: String,
    var lastName: String,
    var bio: String?,
    var projects: List<Project>,
    var email: String,
    var phoneNumber: String,
    var gitHubUrl: String,
    var status: String
)