@file:JvmName("CalendarMainFragmentKt")

package com.intive.patronative.calendar_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import com.intive.patronative.R
import androidx.navigation.fragment.findNavController
import com.intive.patronative.calendar_module.components.*


class CalendarHomeFragment : Fragment() {

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.theme?.applyStyle(R.style.ThemeDialogFullScreen, true)
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    CalendarHomeLayout(
                        { findNavController().navigate(R.id.action_calendarFragment_to_addEventFragment) },
                        { findNavController().navigate(R.id.action_calendarFragment_to_dayFragment) })
                    ChoosePeriodDialog()
                }
            }
        }
    }
}
