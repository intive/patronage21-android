package com.example.patron_a_tive.calendar_module

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.example.patron_a_tive.R
import com.example.patron_a_tive.calendar_module.components.*
import java.util.*


class AddEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                AddEventFragmentLayout()
            }
        }
    }

    private fun getDate(): String {
        val day = Calendar.getInstance()[Calendar.DAY_OF_MONTH].toString()
        val month = Calendar.getInstance()[Calendar.MONTH].toString()
        val year = Calendar.getInstance()[Calendar.YEAR].toString()

        return "$day.$month.$year"
    }

    private fun getStartTime(): String {
        val hour = (Calendar.getInstance()[Calendar.HOUR_OF_DAY] + 1).toString()
        return "$hour:00"
    }

    private fun getEndTime(): String {
        val hour = (Calendar.getInstance()[Calendar.HOUR_OF_DAY] + 2).toString()
        return "$hour:00"
    }

    @Composable
    fun AddEventFragmentLayout() {

        val c: Calendar = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]

        val date = remember {
            mutableStateOf(getDate())
        }

        val timeStart = remember {
            mutableStateOf(getStartTime())
        }

        val timeEnd = remember {
            mutableStateOf(getEndTime())
        }

        val startTimePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                timeStart.value = "$hourOfDay:$minute"
            }, hour, minute, true
        )

        val endTimePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                timeEnd.value = "$hourOfDay:$minute"
            }, hour, minute, true
        )

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _: DatePicker, year: Int, month: Int, day: Int ->
                date.value = "$day.$month.$year"
            }, year, month, day
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {

                HeaderLarge(stringResource(R.string.add_event))

                InputText()

                Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                    PickerRow(stringResource(R.string.date_label), date.value, datePickerDialog)
                    PickerRow(
                        stringResource(R.string.start_hour_label),
                        timeStart.value,
                        startTimePickerDialog
                    )
                    PickerRow(
                        stringResource(R.string.end_hour_label),
                        timeEnd.value,
                        endTimePickerDialog
                    )
                }

                HeaderMedium(
                    text = stringResource(R.string.add_event_checkbox_header),
                    Modifier.padding(bottom = 24.dp)
                )

                CheckboxComponent(stringResource(R.string.checkbox_js_label))
                CheckboxComponent(stringResource(R.string.checkbox_java_label))
                CheckboxComponent(stringResource(R.string.checkbox_qa_label))
                CheckboxComponent(stringResource(R.string.checkbox_mobile_label))
            }

            Column {
                // TODO: modify onClick handlers
                OKButton(stringResource(R.string.accept_new_event)) { findNavController().popBackStack() }
                CancelButton(stringResource(R.string.reject_new_event)) { findNavController().popBackStack() }
            }
        }
    }
}

