package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.tech_groups.presentation.screens.MainScreen
import com.intive.tech_groups.presentation.viewmodels.MainViewModel
import com.intive.ui.PatronativeTheme


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    MainScreen(viewModel)
                }
            }
        }
    }
}
