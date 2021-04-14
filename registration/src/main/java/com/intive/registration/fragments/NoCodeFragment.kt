package com.intive.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.registration.screens.NoCodeScreen
import com.intive.registration.viewmodels.NoCodeViewModel
import com.intive.ui.PatronativeTheme


class NoCodeFragment : Fragment() {

    private val viewmodel: NoCodeViewModel by viewModels()
    private val args: NoCodeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewmodel.firstEmail = args.email
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    NoCodeScreen(viewmodel, findNavController())
                }
            }
        }
    }
}
