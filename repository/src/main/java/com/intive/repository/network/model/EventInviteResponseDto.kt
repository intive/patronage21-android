package com.intive.repository.network.model

data class EventInviteResponseDto(
    val userId: Long,
    val eventId: Long,
    val inviteResponse: String
)