package com.intive.calendar.utils

import android.os.Parcelable
import com.intive.repository.domain.model.User
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class EventBundle(
    val date: String,
    val time: String,
    val name: String,
    val invite: String,
    val users: @RawValue List<User>
): Parcelable