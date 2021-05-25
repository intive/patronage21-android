package com.intive.repository.network.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.AuditDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AuditDtoMapper : DomainMapper<AuditDto, Audit> {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun mapToDomainModel(model: AuditDto): Audit {

        val dateFormat = "uuuu-MM-dd'T'HH:mm:ssXXXXX"

        val format = DateTimeFormatter.ofPattern(dateFormat, Locale.GERMAN)
        val offsetDateTime = ZonedDateTime.parse(model.date, format).toOffsetDateTime()

        return Audit(
                id = model.id,
                title = model.title,
                date = offsetDateTime,
                userName = model.userName
        )
    }

    override fun mapFromDomainModel(domainModel: Audit): AuditDto {
        return AuditDto(
                id = domainModel.id,
                title = domainModel.title,
                date = domainModel.date.toString(),
                userName = domainModel.userName
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toDomainList(initial: List<AuditDto>): List<Audit>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Audit>): List<AuditDto>{
        return initial.map { mapFromDomainModel(it) }
    }

}