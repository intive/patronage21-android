package com.intive.shared

import android.os.Parcelable
import com.intive.repository.domain.model.User
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class EventParcelable(
    val id: Long,
    val date: String,
    val time: String,
    val name: String,
    val inviteResponse: String,
    val users: @RawValue List<User>,
    val active: Boolean
): Parcelable