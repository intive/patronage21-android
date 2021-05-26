package com.intive.calendar.utils

sealed class EventChannel {
    object AddEventError: EventChannel()
    object EditEventError: EventChannel()
    object InvalidInput: EventChannel()
    object InvalidDate: EventChannel()
    object InvalidTime: EventChannel()
    object InvalidCheckboxes: EventChannel()
    object AddEventSuccess: EventChannel()
    object EditEventSuccess: EventChannel()
}
