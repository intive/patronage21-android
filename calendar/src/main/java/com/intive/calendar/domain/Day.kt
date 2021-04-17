package com.intive.calendar.domain

import java.util.*

data class Day(
    val index: Int,
    val events: List<Event>
)

data class DayWeek(
    val date: Calendar,
    val events: List<Event>
)