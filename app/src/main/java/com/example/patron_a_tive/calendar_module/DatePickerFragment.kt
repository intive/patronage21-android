package com.example.patron_a_tive.calendar_module

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*
import com.example.patron_a_tive.ui.activity.NavActivity

class DatePickerFragment : DialogFragment(),
    OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]

        return DatePickerDialog(requireActivity(), activity as NavActivity?, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
    }


}