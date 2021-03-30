package com.example.patron_a_tive.calendar_module

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import java.util.*
import com.example.patron_a_tive.R
import com.example.patron_a_tive.ui.activity.NavActivity
import kotlinx.android.synthetic.main.fragment_add_event.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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