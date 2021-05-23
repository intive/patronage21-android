package com.intive.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.registration.screens.NoCodeScreen
import com.intive.registration.viewmodels.NoCodeViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class NoCodeFragment : Fragment() {

    private val viewmodel: NoCodeViewModel by viewModel<NoCodeViewModel>()
    private val args: NoCodeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(args) {
            viewmodel.firstEmail = email
            viewmodel.login = login
        }
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    NoCodeScreen(viewmodel, findNavController())
                }
            }
        }
    }
}
