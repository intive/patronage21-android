package com.intive.calendar.utils

sealed class InviteResponseChannel {
    object Error : InviteResponseChannel()
}