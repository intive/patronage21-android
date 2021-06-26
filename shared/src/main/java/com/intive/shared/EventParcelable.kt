package com.intive.shared

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventParcelable(
    val id: String,
    val date: String,
    val time: String,
    val name: String,
    val description: String,
    val active: Boolean
): Parcelable