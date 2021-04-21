package com.intive.repository.network.util


import com.intive.repository.domain.model.Event2
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.EventDto

class EventDtoMapper : DomainMapper<EventDto, Event2> {
    override fun mapToDomainModel(model: EventDto): Event2 {
        return Event2(
            id = model.id.toInt(),
            date = model.date,
            timeStart = model.timeStart,
            timeEnd = model.timeEnd,
            name = model.name,
            users = model.users
        )
    }

    override fun mapFromDomainModel(domainModel: Event2): EventDto {
        return EventDto(
            id = domainModel.id.toString(),
            date = domainModel.date,
            timeStart = domainModel.timeStart,
            timeEnd = domainModel.timeEnd,
            name = domainModel.name,
            users = domainModel.users
        )
    }
}