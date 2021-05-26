package com.intive.repository.network.model

data class EditEventDto(
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val groups: String
)