package com.intive.calendar.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEditEventViewModel
import com.intive.calendar.components.EventForm
import com.intive.calendar.utils.extractTimeFromString
import com.intive.shared.EventParcelable
import com.intive.shared.stringToCalendar

@ExperimentalComposeUiApi
@Composable
fun EditEventScreen(
    context: Context,
    event: EventParcelable,
    popBackStack: () -> Unit,
    addEditEventViewModel: AddEditEventViewModel,
    refreshEventsList: () -> Unit
) {

    val time = extractTimeFromString(event.time)

    addEditEventViewModel.setStartDate(stringToCalendar(event.date))
    addEditEventViewModel.setInputValue(event.name)
    addEditEventViewModel.setTimeStart(time[0], time[1])
    addEditEventViewModel.setTimeEnd(time[2], time[3])

    EventForm(
        titleText = stringResource(R.string.edit_event_header),
        addEditEventViewModel = addEditEventViewModel,
        context = context,
        onClick = { addEditEventViewModel.editEvent(refreshEventsList, popBackStack, event.id) }
    )

}