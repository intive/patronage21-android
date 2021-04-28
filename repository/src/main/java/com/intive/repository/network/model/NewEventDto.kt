package com.intive.repository.network.model

data class NewEventDto(
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val groups: List<String>
)