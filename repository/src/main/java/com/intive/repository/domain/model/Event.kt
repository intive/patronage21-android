package com.intive.repository.domain.model

data class Event(
    val _id: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val description: String?,
)
