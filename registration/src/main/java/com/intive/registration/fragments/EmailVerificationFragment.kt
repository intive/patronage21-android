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
import com.intive.registration.screens.EmailVerificationScreen
import com.intive.registration.viewmodels.EmailVerificationViewModel


class EmailVerificationFragment : Fragment() {

    private val viewmodel: EmailVerificationViewModel by viewModels()
    val args: EmailVerificationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val email = args.email
        viewmodel.email = email
        return ComposeView(requireContext()).apply {
            setContent {
                EmailVerificationScreen(viewmodel, findNavController())
            }
        }
    }
}
