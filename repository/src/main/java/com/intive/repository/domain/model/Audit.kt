package com.intive.repository.domain.model

data class Audit(
    val id: Long,
    val title: String,
    val date: String,
    val userName: String
)