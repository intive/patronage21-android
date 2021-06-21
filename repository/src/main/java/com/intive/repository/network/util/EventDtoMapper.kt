package com.intive.repository.network.util


import com.intive.repository.domain.model.Event
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.EventDto

class EventDtoMapper : DomainMapper<EventDto, Event> {
    override fun mapToDomainModel(model: EventDto): Event {
        return Event(
            _id = model._id,
            title = model.title,
            startDate = model.startDate,
            endDate = model.endDate,
            description = model.description,
        )
    }

    override fun mapFromDomainModel(domainModel: Event): EventDto {
        return EventDto(
            _id = domainModel._id.toString(),
            title = domainModel.title,
            startDate = domainModel.startDate,
            endDate = domainModel.endDate,
            description = domainModel.description,
        )
    }
}