package com.intive.repository.network.util

import com.intive.repository.domain.model.EditEvent
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.EditEventDto

class EditEventDtoMapper : DomainMapper<EditEventDto, EditEvent> {
    override fun mapToDomainModel(model: EditEventDto): EditEvent {
        return EditEvent(
            title = model.title,
            startDate = model.startDate,
            endDate = model.endDate,
            description = model.description
        )
    }

    override fun mapFromDomainModel(domainModel: EditEvent): EditEventDto {
        return EditEventDto(
            title = domainModel.title,
            startDate = domainModel.startDate,
            endDate = domainModel.endDate,
            description = domainModel.description
        )
    }
}