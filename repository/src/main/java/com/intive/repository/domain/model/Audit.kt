package com.intive.repository.domain.model

import java.time.OffsetDateTime

data class Audit(
        val id: String,
        val title: String,
        val date: OffsetDateTime,
        val userName: String
)