package com.intive.repository.domain.model

data class StageDetails(
    val id: Long?,
    val number: Long?,
    val description: String?,
    val events: List<Event>,
    val isStageCompleted: Boolean?,
    val completionLevel: String?,
    val areMaterialsAvailable: Boolean?
)