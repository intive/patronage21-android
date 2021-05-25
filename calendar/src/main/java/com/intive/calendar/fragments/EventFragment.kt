package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.screens.EventScreenLayout
import com.intive.calendar.utils.InviteResponseChannel
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.calendar.viewmodels.EventViewModel
import com.intive.shared.EventParcelable
import com.intive.ui.PatronativeTheme
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventFragment : Fragment() {

    private val eventViewModel by viewModel<EventViewModel>()
    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        lifecycleScope.launchWhenStarted {
            eventViewModel.inviteResponseFlow.collect { event ->
                when (event) {
                    is InviteResponseChannel.Error -> {
                        Snackbar.make(
                            requireView(),
                            requireContext().getString(R.string.event_invite_response_error_msg),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        lateinit var event: EventParcelable
        val safeArgs: EventFragmentArgs by navArgs()

        event = safeArgs.eventInfoParcelable ?: Gson().fromJson(safeArgs.eventInfo, EventParcelable::class.java)

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EventScreenLayout(
                        eventViewModel = eventViewModel,
                        navController = findNavController(),
                        updateInviteResponse = eventViewModel::updateInviteResponse,
                        event = event
                    ) { calendarHomeViewModel.refreshEventsList() }
                }
            }
        }
    }
}