package com.intive.shared

import com.intive.repository.domain.model.User
import kotlinx.parcelize.RawValue


data class EventBundle(
    val id: Long,
    val date: String,
    val time: String,
    val name: String,
    val inviteResponse: String,
    val users: @RawValue List<User>,
    val active: Boolean
)