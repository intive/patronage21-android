package com.intive.users.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.presentation.composables.screens.DetailsScreen

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mock = viewModel.user
        val projects = viewModel.projects

        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                val scrollState = rememberScrollState()
                PatronativeTheme {
                    Column(
                        Modifier.verticalScroll(scrollState)
                    ) {

                        DetailsScreen(
                            navController = navController,
                            user = mock,
                            projects = projects
                        )
                    }
                }
            }
        }
    }
}