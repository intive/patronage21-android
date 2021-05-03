package com.intive.repository.domain.model

data class Event(
    val id: Long,
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val inviteResponse: String,
    val users: List<User>
)
