package com.intive.repository.domain.model


data class Event(
    val id: Int,
    val time: String,
    val name: String
)

data class Event2(
    val id: Int,
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val users: List<User>
)
