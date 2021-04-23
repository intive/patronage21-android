package com.intive.calendar.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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