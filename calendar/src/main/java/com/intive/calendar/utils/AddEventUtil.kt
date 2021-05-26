package com.intive.calendar.utils

sealed class AddNewEvent {
    object AddEventError: AddNewEvent()
    object EditEventError: AddNewEvent()
    object InvalidInput: AddNewEvent()
    object InvalidDate: AddNewEvent()
    object InvalidTime: AddNewEvent()
    object InvalidCheckboxes: AddNewEvent()
    object AddEventSuccess: AddNewEvent()
    object EditEventSuccess: AddNewEvent()
}