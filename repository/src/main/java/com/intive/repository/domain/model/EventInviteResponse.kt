package com.intive.repository.domain.model

data class EventInviteResponse(
    val userId: Long,
    val eventId: Long,
    val inviteResponse: String
)