package com.intive.repository.network.model

data class EditEventDto(
    val title: String,
    val startDate: String,
    val endDate: String,
    val description: String?,
)