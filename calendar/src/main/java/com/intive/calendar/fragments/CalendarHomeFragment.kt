@file:JvmName("CalendarMainFragmentKt")

package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHomeLayout
import com.intive.calendar.screens.ChoosePeriodDialog
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class CalendarHomeFragment : Fragment() {

    private val viewModel by viewModel<CalendarHomeViewModel>()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.theme?.applyStyle(R.style.ThemeDialogFullScreen, true)

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    CalendarHomeLayout(findNavController(), viewModel)
                    ChoosePeriodDialog(viewModel)
                }
            }
        }
    }
}
