package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.calendar.screens.DayLayout
import com.intive.ui.PatronativeTheme


class DayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val safeArgs: DayFragmentArgs by navArgs()
        val day = safeArgs.dayInfo

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    DayLayout(
                        navController = findNavController(),
                        day = day!!
                    )
                }
            }
        }
    }
}
