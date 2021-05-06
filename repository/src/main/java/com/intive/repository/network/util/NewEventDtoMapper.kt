package com.intive.repository.network.util

import com.intive.repository.domain.model.NewEvent
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.NewEventDto

class NewEventDtoMapper : DomainMapper<NewEventDto, NewEvent> {
    override fun mapToDomainModel(model: NewEventDto): NewEvent {
        return NewEvent(
            date = model.date,
            timeStart = model.timeStart,
            timeEnd = model.timeEnd,
            name = model.name,
            groups = model.groups
        )
    }

    override fun mapFromDomainModel(domainModel: NewEvent): NewEventDto {
        return NewEventDto(
            date = domainModel.date,
            timeStart = domainModel.timeStart,
            timeEnd = domainModel.timeEnd,
            name = domainModel.name,
            groups = domainModel.groups
        )
    }
}