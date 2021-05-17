package com.intive.shared

import java.util.*

fun getFullDateString(dateString: String): String {

    val calendar = stringToCalendar(dateString)

    return "${weekDaysCalendarClass[calendar[Calendar.DAY_OF_WEEK]]}, ${
        getDateString(
            calendar,
            "."
        )
    }"

}

fun getFullDateString(date: Calendar): String {
    return "${weekDaysCalendarClass[date[Calendar.DAY_OF_WEEK]]}, ${
        getDateString(
            date,
            "."
        )
    }"

}


fun stringToCalendar(dateString: String, separator: String = "-"): Calendar {
    val dateElements = dateString.split(separator)

    val calendar: Calendar = Calendar.getInstance()

    calendar[Calendar.YEAR] = dateElements[2].toInt()
    calendar[Calendar.MONTH] = dateElements[1].toInt() - 1
    calendar[Calendar.DAY_OF_MONTH] = dateElements[0].toInt()

    return calendar
}