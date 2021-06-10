package com.intive.shared

import java.util.*

fun formatDate(date: Calendar): Pair<String, String> {
    val day = if (date[Calendar.DAY_OF_MONTH] < 10) {
        "0${date[Calendar.DAY_OF_MONTH]}"
    } else {
        "${date[Calendar.DAY_OF_MONTH]}"
    }

    val month = if (date[Calendar.MONTH] + 1 < 10) {
        "0${date[Calendar.MONTH] + 1}"
    } else {
        "${date[Calendar.MONTH] + 1}"
    }

    return Pair(day, month)
}

fun getDateString(date: Calendar, separator: String = "-"): String {

    val day = if (date[Calendar.DAY_OF_MONTH] < 10) {
        "0${date[Calendar.DAY_OF_MONTH]}"
    } else {
        "${date[Calendar.DAY_OF_MONTH]}"
    }

    val month = if (date[Calendar.MONTH] + 1 < 10) {
        "0${date[Calendar.MONTH] + 1}"
    } else {
        "${date[Calendar.MONTH] + 1}"
    }

    return "${day}${separator}${month}${separator}${date[Calendar.YEAR]}"
}

fun getDateAndTimeString(date: Calendar, time: String): String {
    val (day, month) = formatDate(date)
    return "${date[Calendar.YEAR]}-${month}-${day}T${time}Z"
}