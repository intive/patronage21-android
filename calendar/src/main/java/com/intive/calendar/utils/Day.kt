package com.intive.calendar.utils

import com.intive.repository.domain.model.Event

data class Day(
    var date: String?,
    var events: List<Event>?
)