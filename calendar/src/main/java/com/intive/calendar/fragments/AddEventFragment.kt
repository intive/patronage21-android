package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.*
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.intive.calendar.screens.AddEventScreen
import com.intive.calendar.viewmodels.AddEditEventViewModel
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.collect
import com.intive.calendar.R
import com.intive.calendar.utils.EventChannel


class AddEventFragment : Fragment() {

    private val addEventViewModel by viewModel<AddEditEventViewModel>()
    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()


    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        
        lifecycleScope.launchWhenStarted {
            addEventViewModel.addEventFlow.collect { event ->
                when (event) {
                    is EventChannel.AddEventError -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.add_event_error_msg),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is EventChannel.AddEventSuccess -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.add_event_success),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is EventChannel.InvalidInput -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.add_event_input_validation_message),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is EventChannel.InvalidDate -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.add_event_date_validation_message),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is EventChannel.InvalidTime -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.add_event_time_validation_message),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    is EventChannel.InvalidCheckboxes -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.add_event_checkbox_validation_message),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    AddEventScreen(
                        requireContext(),
                        { findNavController().popBackStack() },
                        addEventViewModel
                    ) { calendarHomeViewModel.refreshEventsList() }
                }
            }
        }
    }
}