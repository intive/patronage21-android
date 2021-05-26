package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.*
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.calendar.screens.EditEventScreen
import com.intive.calendar.viewmodels.AddEventViewModel
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditEventFragment : Fragment() {

    private val addEventViewModel by viewModel<AddEventViewModel>()
    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()



    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val safeArgs: EditEventFragmentArgs by navArgs()
        val event = safeArgs.eventInfoParcelable!!

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EditEventScreen(
                        requireContext(),
                        event,
                        { findNavController().popBackStack() },
                        addEventViewModel,
                    ) { calendarHomeViewModel.refreshEventsList() }
                }
            }
        }
    }
}