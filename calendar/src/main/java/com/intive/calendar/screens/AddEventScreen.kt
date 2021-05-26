package com.intive.calendar.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEventViewModel
import com.intive.calendar.components.EventForm


@ExperimentalComposeUiApi
@Composable
fun AddEventScreen(
    context: Context,
    popBackStack: () -> Boolean,
    addEventViewModel: AddEventViewModel,
    refreshEventsList: () -> Unit
) {

    EventForm(
        titleText = stringResource(R.string.add_event),
        addEventViewModel = addEventViewModel,
        context = context,
        onClick = {
            addEventViewModel.addNewEvent(refreshEventsList, popBackStack)
        }
    )
}