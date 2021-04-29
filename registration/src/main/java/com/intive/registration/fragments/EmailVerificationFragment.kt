package com.intive.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.registration.screens.EmailVerificationScreen
import com.intive.registration.viewmodels.EmailVerificationViewModel
import com.intive.registration.viewmodels.SharedViewModel
import com.intive.ui.PatronativeTheme


class EmailVerificationFragment : Fragment() {

    private val viewmodel: EmailVerificationViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: EmailVerificationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val email = args.email
        viewmodel.email = email
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EmailVerificationScreen(viewmodel, findNavController(), sharedViewModel)
                }
            }
        }
    }
}
