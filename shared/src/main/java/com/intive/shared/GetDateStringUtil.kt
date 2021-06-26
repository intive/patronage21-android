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

fun getDate(date: Calendar, separator: String = "-"): String {

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

    return "${date[Calendar.YEAR]}${separator}${month}${separator}${day}"
}

fun getDateAndTimeString(date: Calendar): String {
    val (day, month) = formatDate(date)
    val time = formatTime(date[Calendar.HOUR_OF_DAY], date[Calendar.MINUTE])
    return "${date[Calendar.YEAR]}-${month}-${day}T${time}:00Z"
}

fun getDateAndTimeString(date: Calendar, time: String): String {
    val (day, month) = formatDate(date)
    return "${date[Calendar.YEAR]}-${month}-${day}T${time}Z"
}

fun getStartDateString(date: Calendar): String {
    val (day, month) = formatDate(date)
    return "${date[Calendar.YEAR]}-${month}-${day}T00:00:00"
}

fun getEndDateString(date: Calendar): String {
    val (day, month) = formatDate(date)
    return "${date[Calendar.YEAR]}-${month}-${day}T23:59:59"
}