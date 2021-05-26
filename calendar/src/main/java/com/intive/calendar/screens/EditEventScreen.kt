package com.intive.calendar.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEventViewModel
import com.intive.calendar.components.EventForm
import com.intive.calendar.utils.extractTimeFromString
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

    val time = extractTimeFromString(event.time)

    addEventViewModel.setDate(stringToCalendar(event.date, "."))
    addEventViewModel.setInputValue(event.name)
    addEventViewModel.setTimeStart(time[0], time[1])
    addEventViewModel.setTimeEnd(time[2], time[3])

    EventForm(
        titleText = stringResource(R.string.edit_event_header),
        addEventViewModel = addEventViewModel,
        context = context,
        popBackStack = popBackStack,
        refreshEventsList = refreshEventsList
    )

}