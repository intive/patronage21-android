package com.intive.gradebook.composables.gradebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.ui.PatronativeTheme
import com.intive.gradebook.composables.screens.GradebookScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class GradebookFragment : Fragment() {

    private val viewModel by viewModel<GradebookViewModel>()
    private val safeArgs: GradebookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (safeArgs.stage != "null") {
            viewModel.stage = safeArgs.stage
            viewModel.groupStorage = safeArgs.group
        }
        val navController = findNavController()
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    GradebookScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}