package com.intive.repository.network.model

import com.intive.repository.domain.model.Project

data class UpdateUserDto(
    val firstName: String,
    val lastName: String,
    val login: String,
    val projects: List<Project>,
    val email: String,
    val phoneNumber: String,
    val gitHubUrl: String,
    val bio: String?,
)