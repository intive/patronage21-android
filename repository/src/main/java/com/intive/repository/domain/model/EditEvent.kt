package com.intive.repository.domain.model

data class EditEvent(
    val title: String,
    val startDate: String,
    val endDate: String,
    val description: String?,
)