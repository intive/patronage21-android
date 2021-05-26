package com.intive.calendar.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEventViewModel
import com.intive.calendar.components.EventForm
import com.intive.shared.EventParcelable
import com.intive.shared.stringToCalendar

@ExperimentalComposeUiApi
@Composable
fun EditEventScreen(
    context: Context,
    event: EventParcelable,
    popBackStack: () -> Boolean,
    addEventViewModel: AddEventViewModel,
    refreshEventsList: () -> Unit
) {

    addEventViewModel.setDate(stringToCalendar(event.date, "."))
    addEventViewModel.setInputValue(event.name)

    val time = event.time.filterNot { it.isWhitespace() }
    val (startTime, endTime) = time.split("-")

    addEventViewModel.setTimeStart(startTime.split(":")[0].toInt(), startTime.split(":")[1].toInt())
    addEventViewModel.setTimeEnd(endTime.split(":")[0].toInt(), endTime.split(":")[1].toInt())

    EventForm(
        titleText = stringResource(R.string.edit_event_header),
        addEventViewModel = addEventViewModel,
        context = context,
        popBackStack = popBackStack,
        refreshEventsList = refreshEventsList
    )

}