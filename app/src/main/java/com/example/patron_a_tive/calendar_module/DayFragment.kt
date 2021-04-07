package com.example.patron_a_tive.calendar_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.patron_a_tive.calendar_module.components.*

class DayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    DayFragmentLayout(
                        { findNavController().popBackStack() },
                        { findNavController().popBackStack() })
                }
            }
        }
    }
}