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
import com.intive.calendar.screens.EventScreenLayout
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.User
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EventFragment : Fragment() {

    private val calendarHomeViewModel by sharedViewModel<CalendarHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val date = arguments?.getString("date")
        val time = arguments?.getString("time")
        val name = arguments?.getString("name")

        val users = arguments?.getString("users")
        val itemType = object : TypeToken<List<User>>() {}.type
        val usersList = Gson().fromJson<List<User>>(users, itemType)

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    if (date != null && time != null && name != null) {
                        EventScreenLayout(
                            findNavController(),
                            date,
                            time,
                            name,
                            usersList
                        ) { calendarHomeViewModel.refreshCalendar() }
                    }
                }
            }
        }
    }
}