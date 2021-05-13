package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.intive.tech_groups.presentation.screens.GroupDetailsScreen
import com.intive.tech_groups.presentation.screens.MainScreen
import com.intive.ui.PatronativeTheme

class GroupDetailsFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    GroupDetailsScreen()
                }
            }
        }
    }
}