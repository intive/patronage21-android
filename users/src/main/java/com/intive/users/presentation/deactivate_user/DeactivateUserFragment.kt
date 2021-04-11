package com.intive.users.presentation.deactivate_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.presentation.composables.screens.DeactivateUserScreen

class DeactivateUserFragment : Fragment() {
    private val viewModel: DeactivateUserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    DeactivateUserScreen(
                        viewModel = viewModel,
                        navController = findNavController()
                    )
                }
            }
        }
    }





}