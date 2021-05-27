package com.intive.repository.network.util

import com.intive.repository.domain.model.EditEvent
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.EditEventDto

class EditEventDtoMapper : DomainMapper<EditEventDto, EditEvent> {
    override fun mapToDomainModel(model: EditEventDto): EditEvent {
        return EditEvent(
            date = model.date,
            timeStart = model.timeStart,
            timeEnd = model.timeEnd,
            name = model.name,
            groups = model.groups
        )
    }

    override fun mapFromDomainModel(domainModel: EditEvent): EditEventDto {
        return EditEventDto(
            date = domainModel.date,
            timeStart = domainModel.timeStart,
            timeEnd = domainModel.timeEnd,
            name = domainModel.name,
            groups = domainModel.groups
        )
    }
}