package com.intive.repository.network.model

import com.intive.repository.domain.model.User

data class EventDto(
    val _id: String,
    val title: String,
    val startDate: String,
    val endDate: String,
    val description: String?,
)