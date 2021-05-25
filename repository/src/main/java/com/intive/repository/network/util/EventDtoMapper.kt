package com.intive.repository.network.util


import com.intive.repository.domain.model.Event
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.EventDto

class EventDtoMapper : DomainMapper<EventDto, Event> {
    override fun mapToDomainModel(model: EventDto): Event {
        return Event(
            id = model.id.toLong(),
            date = model.date,
            timeStart = model.timeStart,
            timeEnd = model.timeEnd,
            name = model.name,
            inviteResponse = model.inviteResponse,
            users = model.users
        )
    }

    override fun mapFromDomainModel(domainModel: Event): EventDto {
        return EventDto(
            id = domainModel.id.toString(),
            date = domainModel.date,
            timeStart = domainModel.timeStart,
            timeEnd = domainModel.timeEnd,
            name = domainModel.name,
            inviteResponse = domainModel.inviteResponse,
            users = domainModel.users
        )
    }
}