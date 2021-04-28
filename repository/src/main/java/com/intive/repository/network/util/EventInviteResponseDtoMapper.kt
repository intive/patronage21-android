package com.intive.repository.network.util

import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.EventInviteResponseDto


class EventInviteResponseDtoMapper : DomainMapper<EventInviteResponseDto, EventInviteResponse> {
    override fun mapToDomainModel(model: EventInviteResponseDto): EventInviteResponse {
        return EventInviteResponse(
            userId = model.userId,
            eventId = model.eventId,
            inviteResponse = model.inviteResponse
        )
    }

    override fun mapFromDomainModel(domainModel: EventInviteResponse): EventInviteResponseDto {
        return EventInviteResponseDto(
            userId = domainModel.userId,
            eventId = domainModel.eventId,
            inviteResponse = domainModel.inviteResponse
        )
    }
}