package com.example.patron_a_tive.calendar_module.utils

import java.util.*

fun isDateSame(c1: Calendar, c2: Calendar): Boolean {
    return c1[Calendar.YEAR] == c2[Calendar.YEAR] && c1[Calendar.MONTH] == c2[Calendar.MONTH] && c1[Calendar.DAY_OF_MONTH] == c2[Calendar.DAY_OF_MONTH]
}