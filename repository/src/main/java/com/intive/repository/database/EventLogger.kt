package com.intive.repository.database

import com.intive.repository.Repository
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EventLogger(
    private val repository: Repository
) {
    suspend fun log(logMessage: String, login: String = "") {
        val dateFormat = "uuuu-MM-dd'T'HH:mm:ssXXXXX"
        val format = DateTimeFormatter.ofPattern(dateFormat, Locale.GERMAN)
        repository.insertAudit(logMessage, OffsetDateTime.parse(OffsetDateTime.now().format(format)), login)
    }
}