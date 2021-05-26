package com.intive.calendar.utils

fun extractTimeFromString(timeString: String): Array<Int> {
    val time = timeString.filterNot { it.isWhitespace() }
    val (startTime, endTime) = time.split("-")
    val startHour = startTime.split(":")[0].toInt()
    val startMinute = startTime.split(":")[1].toInt()
    val endHour = endTime.split(":")[0].toInt()
    val endMinute = endTime.split(":")[1].toInt()

    return arrayOf(startHour, startMinute, endHour, endMinute)
}