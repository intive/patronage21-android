package com.intive.calendar.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R
import com.intive.calendar.viewmodels.AddEventViewModel
import java.util.*
import com.intive.calendar.components.*
import com.intive.calendar.utils.getDateString
import com.intive.ui.components.TitleText

@ExperimentalComposeUiApi
@Composable
fun AddEventScreen(
    context: Context,
    popBackStack: () -> Boolean,
    addEventViewModel: AddEventViewModel,
    refreshCalendar: () -> Unit
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

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            TitleText(stringResource(R.string.add_event), Modifier.padding(bottom = 24.dp))
            InputText(inputValue!!, addEventViewModel::setInputValue)

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

            TitleText(
                stringResource(R.string.add_event_checkbox_header),
                Modifier.padding(bottom = 24.dp),
                MaterialTheme.typography.h6,
                Color.Black
            )


            if (technologyGroups?.isNotEmpty() == true) {
                technologyGroups!!.forEach {
                    CheckboxComponent(
                        it,
                        addEventViewModel::updateSelectedTechnologyGroups
                    )
                }
            }
        }

        Column {
            OKButton(stringResource(R.string.accept_new_event)) {
                addEventViewModel.validateForm(
                    popBackStack, refreshCalendar
                )
            }

            CancelButton(stringResource(R.string.reject_new_event)) {
                refreshCalendar()
                popBackStack()
            }
        }
    }
}