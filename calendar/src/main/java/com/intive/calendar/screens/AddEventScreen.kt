package com.intive.calendar.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEventViewModel
import java.util.*
import com.intive.calendar.components.*
import com.intive.ui.components.TitleText
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.platform.LocalFocusManager
import com.intive.shared.getDateString
import com.intive.ui.components.CheckBoxesList
import com.intive.ui.components.LayoutContainer
import com.intive.ui.components.PrimaryButton

@ExperimentalComposeUiApi
@Composable
fun AddEventScreen(
    context: Context,
    popBackStack: () -> Boolean,
    addEventViewModel: AddEventViewModel,
    refreshEventsList: () -> Unit
) {

    val date by addEventViewModel.date.observeAsState()
    val hourStart by addEventViewModel.hourStart.observeAsState()
    val hourEnd by addEventViewModel.hourEnd.observeAsState()
    val minutesStart by addEventViewModel.minutesStart.observeAsState()
    val minutesEnd by addEventViewModel.minutesEnd.observeAsState()
    val inputValue by addEventViewModel.inputValue.observeAsState()
    val technologyGroups by addEventViewModel.technologyGroups.observeAsState()

    val c: Calendar = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c[Calendar.HOUR_OF_DAY]
    val minute = c[Calendar.MINUTE]

    val datePickerDialog = DatePickerDialog(
        context, { _: DatePicker, year: Int, month: Int, day: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            addEventViewModel.setDate(calendar)
        }, year, month, day
    )

    val startTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            addEventViewModel.setTimeStart(hourOfDay, minute)
        }, hour, minute, true
    )

    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            addEventViewModel.setTimeEnd(hourOfDay, minute)
        }, hour, minute, true
    )

    val lazyListState = rememberLazyListState()

    LayoutContainer {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {

            LazyColumn(state = lazyListState, modifier = Modifier.weight(1f)) {
                item {

                    Column {

                        TitleText(
                            stringResource(R.string.add_event),
                            Modifier.padding(bottom = 24.dp)
                        )

                        InputText(
                            inputValue!!,
                            addEventViewModel::setInputValue,
                            LocalFocusManager.current
                        )

                        Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            PickerRow(
                                stringResource(R.string.date_label),
                                getDateString(date!!, "."),
                                datePickerDialog
                            )
                            PickerRow(
                                stringResource(R.string.start_hour_label),
                                "$hourStart:$minutesStart",
                                startTimePickerDialog
                            )
                            PickerRow(
                                stringResource(R.string.end_hour_label),
                                "$hourEnd:$minutesEnd",
                                endTimePickerDialog
                            )
                        }

                        if (technologyGroups?.isNotEmpty() == true) {
                            CheckBoxesList(
                                title = stringResource(R.string.add_event_checkbox_header),
                                onErrorText = "",
                                items = technologyGroups!!,
                                onItemSelected = addEventViewModel::updateSelectedTechnologyGroups,
                                modifier = Modifier.padding(bottom = 14.dp),
                                style = MaterialTheme.typography.h6
                            )
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }

            Column {
                PrimaryButton(stringResource(R.string.accept_new_event)) {
                    addEventViewModel.isFormValid(
                        popBackStack, refreshEventsList
                    )
                }
            }
        }
    }
}