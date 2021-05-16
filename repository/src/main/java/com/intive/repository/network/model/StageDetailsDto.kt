package com.intive.repository.network.model

import com.intive.repository.domain.model.Event

data class StageDetailsDto(
    val id: Long,
    val number: Long,
    val description: String,
    val events: List<Event>,
    val isStageCompleted: Boolean,
    val completionLevel: String,
    val areMaterialsAvailable: Boolean
)