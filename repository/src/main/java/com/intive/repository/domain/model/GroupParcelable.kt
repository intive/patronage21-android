package com.intive.repository.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupParcelable(
    val id: String,
    var name: String,
    var description: String,
    var technologies: List<String>
): Parcelable