package com.intive.calendar.utils

import android.os.Parcelable
import com.intive.repository.domain.model.Event
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class DayBundle(
    val date: String,
    val events: @RawValue List<Event>,
    val active: Boolean
): Parcelable