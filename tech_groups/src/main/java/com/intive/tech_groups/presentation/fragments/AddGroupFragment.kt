package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.intive.tech_groups.presentation.screens.AddGroupScreen
import com.intive.tech_groups.presentation.viewmodels.AddGroupViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddGroupFragment : Fragment() {

    private val viewModel: AddGroupViewModel by viewModel<AddGroupViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    AddGroupScreen(viewModel, findNavController())
                }
            }
        }
    }
}
