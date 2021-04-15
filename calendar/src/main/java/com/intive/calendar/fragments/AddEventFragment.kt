package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.*
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.intive.calendar.components.*
import com.intive.ui.PatronativeTheme


class AddEventFragment : Fragment() {

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    view?.let { AddEventLayout(it, requireContext(), findNavController()) }
                }
            }
        }
    }
}

