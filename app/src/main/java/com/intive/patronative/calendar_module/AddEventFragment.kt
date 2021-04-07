package com.intive.patronative.calendar_module

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.intive.patronative.calendar_module.components.*
import com.intive.patronative.calendar_module.viewmodels.AddEventViewModel
import com.intive.patronative.R
import java.util.*


class AddEventFragment : Fragment() {

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    AddEventFragmentLayout()
                }
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    fun AddEventFragmentLayout(addEventViewModel: AddEventViewModel = viewModel()) {

        val date: String? by addEventViewModel.date.observeAsState()
        val timeStart: String? by addEventViewModel.timeStart.observeAsState()
        val timeEnd: String? by addEventViewModel.timeEnd.observeAsState()

        val inputValue: String? by addEventViewModel.inputValue.observeAsState()

        val checkbox1: Boolean? by addEventViewModel.checkbox1.observeAsState()
        val checkbox2: Boolean? by addEventViewModel.checkbox2.observeAsState()
        val checkbox3: Boolean? by addEventViewModel.checkbox3.observeAsState()
        val checkbox4: Boolean? by addEventViewModel.checkbox4.observeAsState()

        val c: Calendar = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c[Calendar.HOUR_OF_DAY]
        val minute = c[Calendar.MINUTE]

        val datePickerDialog = DatePickerDialog(
            requireContext(), { _: DatePicker, year: Int, month: Int, day: Int ->
                addEventViewModel.setDate("$day.$month.$year")
            }, year, month, day
        )

        val startTimePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                addEventViewModel.setTimeStart("$hourOfDay:$minute")
            }, hour, minute, true
        )

        val endTimePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                addEventViewModel.setTimeEnd("$hourOfDay:$minute")
            }, hour, minute, true
        )



        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {

                HeaderLarge(stringResource(R.string.add_event))
                InputText(inputValue!!)

                Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                    PickerRow(
                        stringResource(R.string.date_label),
                        date!!,
                        datePickerDialog
                    )
                    PickerRow(
                        stringResource(R.string.start_hour_label),
                        timeStart!!,
                        startTimePickerDialog
                    )
                    PickerRow(
                        stringResource(R.string.end_hour_label),
                        timeEnd!!,
                        endTimePickerDialog
                    )
                }

                HeaderMedium(
                    text = stringResource(R.string.add_event_checkbox_header),
                    Modifier.padding(bottom = 24.dp)
                )

                CheckboxComponent(
                    stringResource(R.string.checkbox_js_label),
                    checkbox1!!
                ) { addEventViewModel.setCheckbox1() }
                CheckboxComponent(
                    stringResource(R.string.checkbox_java_label),
                    checkbox2!!
                ) { addEventViewModel.setCheckbox2() }
                CheckboxComponent(
                    stringResource(R.string.checkbox_qa_label),
                    checkbox3!!
                ) { addEventViewModel.setCheckbox3() }
                CheckboxComponent(
                    stringResource(R.string.checkbox_mobile_label),
                    checkbox4!!
                ) { addEventViewModel.setCheckbox4() }


            }

            Column {
                // TODO: modify onClick handlers
                OKButton(stringResource(R.string.accept_new_event)) {
                    findNavController().navigate(R.id.action_addEventFragment_to_calendarFragment)
                }
                CancelButton(stringResource(R.string.reject_new_event)) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}

