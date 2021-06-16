package com.intive.users.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.presentation.composables.screens.UsersScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment() {

    private val viewModel by viewModel<UsersViewModel>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    UsersScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}