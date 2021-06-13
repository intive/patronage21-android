package com.intive.repository.network.util

import com.intive.repository.domain.model.NewEvent
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.NewEventDto

class NewEventDtoMapper : DomainMapper<NewEventDto, NewEvent> {
    override fun mapToDomainModel(model: NewEventDto): NewEvent {
        return NewEvent(
            title = model.title,
            description = model.description,
            startDate = model.startDate,
            endDate = model.endDate
        )
    }

    override fun mapFromDomainModel(domainModel: NewEvent): NewEventDto {
        return NewEventDto(
            title = domainModel.title,
            description = domainModel.description,
            startDate = domainModel.startDate,
            endDate = domainModel.endDate
        )
    }
}