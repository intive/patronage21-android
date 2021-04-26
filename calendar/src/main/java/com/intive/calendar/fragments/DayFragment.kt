package com.intive.calendar.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.intive.calendar.R
import com.intive.calendar.screens.DayLayout
import com.intive.calendar.utils.DayBundle
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DayFragment : Fragment() {

    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.theme?.applyStyle(R.style.ThemeDialogFullScreen, true)

        lateinit var day: DayBundle
        val bundle = this.arguments
        if (bundle != null) {
            day = bundle.getParcelable<Parcelable>("day") as DayBundle
        }

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    DayLayout(
                        findNavController(), day.date, day.events
                    ) { calendarHomeViewModel.refreshCalendar() }
                }
            }
        }
    }
}
