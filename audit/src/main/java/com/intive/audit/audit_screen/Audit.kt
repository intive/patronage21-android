package com.intive.audit.audit_screen

import java.util.*

data class Audit(
    val id: Long,
    val date: Date,
    val eventTitle: String,
    val userName: String
)