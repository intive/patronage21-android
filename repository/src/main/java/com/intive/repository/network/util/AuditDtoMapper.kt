package com.intive.repository.network.util

import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.AuditDto

class AuditDtoMapper : DomainMapper<AuditDto, Audit> {

    override fun mapToDomainModel(model: AuditDto): Audit {
        return Audit(
                id = model.id,
                title = model.title,
                date = model.date,
                userName = model.userName
        )
    }

    override fun mapFromDomainModel(domainModel: Audit): AuditDto {
        return AuditDto(
                id = domainModel.id,
                title = domainModel.title,
                date = domainModel.date,
                userName = domainModel.userName
        )
    }

    fun toDomainList(initial: List<AuditDto>): List<Audit>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Audit>): List<AuditDto>{
        return initial.map { mapFromDomainModel(it) }
    }

}