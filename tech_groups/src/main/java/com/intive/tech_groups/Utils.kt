package com.intive.tech_groups

import java.util.*


val weekDaysCalendarClass = listOf("", "Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota")

fun getFullDateString(dateString: String, separator: String = "-"): String {
    val dateElements = dateString.split(separator)

    val calendar: Calendar = Calendar.getInstance()

    calendar[Calendar.YEAR] = dateElements[2].toInt()
    calendar[Calendar.MONTH] = dateElements[1].toInt() - 1
    calendar[Calendar.DAY_OF_MONTH] = dateElements[0].toInt()

    return "${weekDaysCalendarClass[calendar[Calendar.DAY_OF_WEEK]]}, ${getDateString(calendar, ".")}"

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