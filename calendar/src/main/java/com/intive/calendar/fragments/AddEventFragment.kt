package com.intive.calendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.calendar.screens.AddEventScreen
import com.intive.calendar.viewmodels.AddEventViewModel
import com.intive.ui.PatronativeTheme


class AddEventFragment : Fragment() {

    private val viewModel: AddEventViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    view?.let {
                        AddEventScreen(
                            it,
                            requireContext(),
                            findNavController(),
                            viewModel
                        )
                    }
                }
            }
        }
    }
}

