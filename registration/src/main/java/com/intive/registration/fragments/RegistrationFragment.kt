package com.intive.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.intive.registration.screens.RegistrationScreen
import com.intive.registration.viewmodels.RegistrationViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private val viewModel: RegistrationViewModel by viewModel<RegistrationViewModel>()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    RegistrationScreen(viewModel, findNavController())
                }
            }
        }
    }
}