package com.intive.gradebook.composables.gradebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.gradebook.composables.screens.GradebookScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class GradebookFragment : Fragment() {

    private val viewModel by viewModel<GradebookViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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