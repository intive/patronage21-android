package com.intive.calendar.utils

sealed class EventScreenChannel {
    object InviteResponseError : EventScreenChannel()
    object EventDeleteError : EventScreenChannel()
    object EventDeleteSuccess : EventScreenChannel()
}