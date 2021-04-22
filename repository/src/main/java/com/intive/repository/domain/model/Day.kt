package com.intive.repository.domain.model

import java.util.*

data class Day(
    var date: Calendar?,
    var events: List<Event>?
)

data class DayMonth(
    var date: String?,
    var events: List<Event>?
)