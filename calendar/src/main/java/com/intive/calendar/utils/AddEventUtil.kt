package com.intive.calendar.utils

sealed class AddNewEvent {
    object Error: AddNewEvent()
    object InvalidInput: AddNewEvent()
    object InvalidDate: AddNewEvent()
    object InvalidTime: AddNewEvent()
    object InvalidCheckboxes: AddNewEvent()
}