package com.intive.calendar.utils

import java.util.*

fun getMonthAndYearString(date: Calendar): String {

    val month = if (date[Calendar.MONTH] + 1 < 10) {
        "0${date[Calendar.MONTH] + 1}"
    } else {
        "${date[Calendar.MONTH] + 1}"
    }

    return "${month}.${date[Calendar.YEAR]}"
}