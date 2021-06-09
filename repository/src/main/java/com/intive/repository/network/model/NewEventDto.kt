package com.intive.repository.network.model

data class NewEventDto(
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String
)