package com.intive.gradebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.gradebook.composables.screens.AddColumnScreen
import com.intive.gradebook.composables.screens.GradebookScreen

class AddColumnFragment : Fragment() {
    private val viewModel: AddColumnViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    AddColumnScreen(
                        viewModel = viewModel,
                        navController = findNavController()
                    )
                }
            }
        }
    }





}