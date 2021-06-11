package com.intive.repository.database.util

import com.intive.repository.database.audits.AuditEntity
import com.intive.repository.domain.model.Audit
import com.intive.repository.network.model.AuditDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AuditEntityMapper {

    fun mapToEntityModel(model: Audit): AuditEntity{
        return AuditEntity(
            id = 1,
            title = model.title,
            date = model.date.toString(),
            userName = model.userName
        )
    }

    fun mapFromEntityModel(auditEntity: AuditEntity): Audit{
        val dateFormat = "uuuu-MM-dd'T'HH:mm:ssXXXXX"

        val format = DateTimeFormatter.ofPattern(dateFormat, Locale.GERMAN)
        val offsetDateTime = ZonedDateTime.parse(auditEntity.date, format).toOffsetDateTime()
        return Audit(
            id = auditEntity.id.toString(),
            title = auditEntity.title!!,
            date = offsetDateTime,
            userName = auditEntity.userName!!
        )
    }

    fun toModelList(initial: List<AuditEntity>): List<Audit>{
        return initial.map { mapFromEntityModel(it) }
    }
}