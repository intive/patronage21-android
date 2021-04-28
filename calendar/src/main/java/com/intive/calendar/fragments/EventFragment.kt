package com.intive.calendar.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.intive.calendar.screens.EventScreenLayout
import com.intive.calendar.utils.EventBundle
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EventFragment : Fragment() {

    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        lateinit var event: EventBundle
        val bundle = this.arguments
        if (bundle != null) {
            event = bundle.getParcelable<Parcelable>("event") as EventBundle
        }

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EventScreenLayout(
                        findNavController(),
                        event.date,
                        event.time,
                        event.name,
                        event.users
                    ) { calendarHomeViewModel.refreshCalendar() }
                }
            }
        }
    }
}