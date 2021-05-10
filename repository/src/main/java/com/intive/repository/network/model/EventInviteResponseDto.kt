package com.intive.repository.network.model

data class EventInviteResponseDto(
    val userId: String,
    val eventId: String,
    val inviteResponse: String
)