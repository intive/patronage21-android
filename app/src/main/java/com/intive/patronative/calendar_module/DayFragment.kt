package com.intive.patronative.calendar_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intive.patronative.R
import com.intive.patronative.calendar_module.components.DayLayout
import com.intive.patronative.calendar_module.viewmodels.CalendarHomeViewModel


class DayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.theme?.applyStyle(R.style.ThemeDialogFullScreen, true)

        val date = arguments?.getString("header")
        val events= arguments?.getString("events")

        val itemType = object : TypeToken<List<CalendarHomeViewModel.Event>>() {}.type
        val eventsList = Gson().fromJson<List<CalendarHomeViewModel.Event>>(events, itemType)

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    if (date != null) {
                        DayLayout(findNavController(), date, eventsList
                        )
                    }
                }
            }
        }
    }
}
