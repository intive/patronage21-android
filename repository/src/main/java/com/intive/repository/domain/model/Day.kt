package com.intive.repository.domain.model

import java.util.*

data class Day(
    val index: Int,
    val events: List<Event>
)

data class DayWeek(
    val date: Calendar?,
    var events: List<Event2>?
)