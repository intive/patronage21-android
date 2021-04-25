package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intive.calendar.R
import com.intive.calendar.screens.DayLayout
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.repository.domain.model.Event
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DayFragment : Fragment() {

    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.theme?.applyStyle(R.style.ThemeDialogFullScreen, true)

        val date = arguments?.getString("header")
        val events = arguments?.getString("events")

        val itemType = object : TypeToken<List<Event>>() {}.type
        val eventsList = Gson().fromJson<List<Event>>(events, itemType)

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    if (date != null) {
                        DayLayout(findNavController(), date, eventsList
                        ) { calendarHomeViewModel.refreshCalendar() }
                    }
                }
            }
        }
    }
}
