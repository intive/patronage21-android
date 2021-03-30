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
import androidx.compose.ui.*


import androidx.compose.material.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_add_event.*


import com.example.patron_a_tive.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEventFragment : Fragment(),
    TimePickerDialog.OnTimeSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var navController: NavController

    var hour: Int = 0
    var minute: Int = 0

    var isStart: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        navController = findNavController()
    }

    override fun onStart() {
        super.onStart()

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


    }

    private fun showDatePickerDialog(v: View?) {
        val newFragment: DialogFragment = DatePickerFragment()
        newFragment.show(parentFragmentManager, "datePicker")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddEventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddEventFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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