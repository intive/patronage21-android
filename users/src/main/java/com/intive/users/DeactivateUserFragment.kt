package com.intive.users

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.composables.screens.DeactivateUserScreen
import com.intive.users.composables.screens.UsersScreen

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