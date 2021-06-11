package com.intive.calendar.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEditEventViewModel
import com.intive.shared.getDateString
import com.intive.ui.components.*
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun EventForm(
    titleText: String,
    addEditEventViewModel: AddEditEventViewModel,
    context: Context,
    onClick: () -> Unit
) {

    val dateStart by addEditEventViewModel.dateStart.observeAsState()
    val dateEnd by addEditEventViewModel.dateEnd.observeAsState()
    val hourStart by addEditEventViewModel.hourStart.observeAsState()
    val hourEnd by addEditEventViewModel.hourEnd.observeAsState()
    val minutesStart by addEditEventViewModel.minutesStart.observeAsState()
    val minutesEnd by addEditEventViewModel.minutesEnd.observeAsState()
    val inputValue by addEditEventViewModel.inputValue.observeAsState()
    val descriptionValue by addEditEventViewModel.descriptionValue.observeAsState()
    val technologyGroups by addEditEventViewModel.technologyGroups.observeAsState()

    val lazyListState = rememberLazyListState()

    val c: Calendar = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c[Calendar.HOUR_OF_DAY]
    val minute = c[Calendar.MINUTE]

    val startDatePickerDialog = DatePickerDialog(
        context, { _: DatePicker, year: Int, month: Int, day: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            addEditEventViewModel.setStartDate(calendar)
        }, year, month, day
    )

    val endDatePickerDialog = DatePickerDialog(
        context, { _: DatePicker, year: Int, month: Int, day: Int ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            addEditEventViewModel.setEndDate(calendar)
        }, year, month, day
    )

    val startTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            addEditEventViewModel.setTimeStart(hourOfDay, minute)
        }, hour, minute, true
    )

    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            addEditEventViewModel.setTimeEnd(hourOfDay, minute)
        }, hour, minute, true
    )

    LayoutContainer {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {

            LazyColumn(state = lazyListState, modifier = Modifier.weight(1f)) {
                item {

                    Column {

                        TitleText(
                            titleText,
                            Modifier.padding(bottom = 24.dp)
                        )

                        InputText(
                            inputValue!!,
                            stringResource(R.string.add_event_hint),
                            addEditEventViewModel::setInputValue,
                            LocalFocusManager.current
                        )

                        Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                            PickerRow(stringResource(R.string.event_start_date_label), getDateString(dateStart!!, "."), "$hourStart:$minutesStart", startDatePickerDialog, startTimePickerDialog)
                            PickerRow(stringResource(R.string.event_end_date_label), getDateString(dateEnd!!, "."), "$hourEnd:$minutesEnd", endDatePickerDialog, endTimePickerDialog)
                        }

                        Text(
                            stringResource(R.string.event_description),
                            modifier = Modifier.padding(bottom = 4.dp),
                            style = MaterialTheme.typography.h6
                        )

                        InputText(
                            textState = descriptionValue!!,
                            label = "",
                            setInputValue = addEditEventViewModel::setDescriptionValue,
                            focusManager = LocalFocusManager.current,
                            modifier = Modifier
                                .height(100.dp)
                                .padding(bottom = 16.dp)
                        )

                        if (technologyGroups?.isNotEmpty() == true) {
                            CheckBoxesList(
                                title = stringResource(R.string.add_event_checkbox_header),
                                onErrorText = "",
                                items = technologyGroups!!,
                                onItemSelected = addEditEventViewModel::updateSelectedTechnologyGroups,
                                modifier = Modifier.padding(bottom = 4.dp),
                                style = MaterialTheme.typography.h6,
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
                PrimaryButton(stringResource(R.string.accept_new_event), onClick = onClick)
            }
        }
    }
}

