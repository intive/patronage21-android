package com.intive.repository.domain.model

data class NewEvent(
    val date: String,
    val timeStart: String,
    val timeEnd: String,
    val name: String,
    val groups: List<String>
)