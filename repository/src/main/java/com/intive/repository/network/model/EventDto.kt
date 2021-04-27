package com.intive.repository.network.model

import com.intive.repository.domain.model.User

data class EventDto(
    val id: String,
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val invite: String,
    val users: List<User>
)