package com.intive.audit.domain

data class Audit(
    val id: Long,
    val title: String,
    val date: String,
    val userName: String
)