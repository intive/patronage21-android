package com.intive.calendar.utils

import java.util.*

fun getDateString(date: Calendar, separator: String = "-"): String {
    // TODO: Fix zeros
    return "${date[Calendar.DAY_OF_MONTH]}${separator}0${date[Calendar.MONTH] + 1}${separator}${date[Calendar.YEAR]}"
}