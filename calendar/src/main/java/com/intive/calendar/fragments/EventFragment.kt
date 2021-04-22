package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.intive.calendar.screens.EventFragmentLayout
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {

    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val date = arguments?.getString("date")
        val time = arguments?.getString("time")
        val name = arguments?.getString("name")

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    if (date != null && time != null && name != null) {
                        EventFragmentLayout(
                            findNavController(),
                            date,
                            time,
                            name
                        ) { calendarHomeViewModel.refreshCalendar() }
                    }
                }
            }
        }
    }
}