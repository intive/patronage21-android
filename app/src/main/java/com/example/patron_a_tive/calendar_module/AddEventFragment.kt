package com.example.patron_a_tive.calendar_module

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_add_event.*


import com.example.patron_a_tive.R
import com.example.patron_a_tive.calendar_module.components.*
import androidx.compose.material.TextField as TextField


class AddEventFragment : Fragment(),
    TimePickerDialog.OnTimeSetListener {

    private lateinit var navController: NavController

    var hour: Int = 0
    var minute: Int = 0
    var isStart: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController()
    }

    override fun onStart() {
        super.onStart()

        /*
        edit_date.setOnClickListener {
            showDatePickerDialog(it)
        }

        edit_time_start?.setOnClickListener {
            isStart = true
            showTimePickerDialog(it)
        }

        edit_time_end?.setOnClickListener {
            showTimePickerDialog(it)
        }

        add_event_ok_button.setOnClickListener {
            navController?.navigate(R.id.action_addEventFragment_to_calendarFragment)
        }
        add_event_cancel_button.setOnClickListener {
            navController?.navigate(R.id.action_addEventFragment_to_calendarFragment)
        }

         */

    }

    private fun showDatePickerDialog(v: View?) {
        val newFragment: DialogFragment = DatePickerFragment()
        newFragment.show(parentFragmentManager, "datePicker")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                AddEventFragmentLayout()
            }
        }
    }

    @Composable
    fun AddEventFragmentLayout() {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {

                HeaderLarge(stringResource(R.string.add_event))

                InputText()

                Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                    PickerRow(stringResource(R.string.date_label), "02.04.2021")
                    PickerRow(stringResource(R.string.start_hour_label), "12:00")
                    PickerRow(stringResource(R.string.end_hour_label), "13:00")
                }

                HeaderMedium(text = stringResource(R.string.add_event_checkbox_header))

                CheckboxComponent(stringResource(R.string.checkbox_js_label))
                CheckboxComponent(stringResource(R.string.checkbox_java_label))
                CheckboxComponent(stringResource(R.string.checkbox_qa_label))
                CheckboxComponent(stringResource(R.string.checkbox_mobile_label))
            }

            Column {
                OKButton(stringResource(R.string.accept_new_event))
                CancelButton(stringResource(R.string.reject_new_event))
            }
        }
    }

    private fun showTimePickerDialog(v: View?) {
        val timePickerDialog = TimePickerDialog(
            activity, this, hour, minute,
            DateFormat.is24HourFormat(requireContext())
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        var hourStr: String = hour.toString()
        var minuteStr: String = minute.toString()
        if (hour < 10) hourStr = "0$hour"
        if (minute < 10) minuteStr = "0$minute"

        if (isStart) {
            time_start_value.text = "$hourStr:$minuteStr"
            isStart = false
        } else {
            time_end_value.text = "$hourStr:$minuteStr"
        }

    }

    private fun findNavController(): NavController {
        val navHostFragment =
            (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}

