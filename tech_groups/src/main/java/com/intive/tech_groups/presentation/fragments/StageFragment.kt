package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.intive.tech_groups.presentation.screens.StageScreen
import com.intive.tech_groups.presentation.viewmodels.StageViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class StageFragment : Fragment() {

    private val stageViewModel by sharedViewModel<StageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    StageScreen(stageViewModel)
                }
            }
        }
    }
}