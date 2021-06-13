package com.intive.calendar.utils

fun formatTime(hour: Int, minutes: Int): String {

    val hourString = when {
        hour < 10 -> "0$hour"
        else -> "$hour"
    }

    val minutesString = when {
        minutes == 0 -> "00"
        minutes < 10 -> "0${minutes}"
        else -> "$minutes"
    }

    return "$hourString:$minutesString"
}

fun timeToString(hour: String, minutes: String): String {

    val hourString = when {
        hour.toInt() < 10 -> "0$hour"
        else -> hour
    }

    val minutesString = when {
        minutes.toInt() == 0 -> "00"
        minutes.toInt() < 10 -> "0${minutes}"
        else -> minutes
    }

    return "$hourString:$minutesString"
}