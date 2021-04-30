package com.intive.calendar.utils

import java.util.*

data class CurrentMonth(val header: List<String> = calendarHeader, val offset: List<String>, val days: List<Calendar>)