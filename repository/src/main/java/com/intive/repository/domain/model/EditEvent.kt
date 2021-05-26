package com.intive.repository.domain.model

data class EditEvent(
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val groups: String
)