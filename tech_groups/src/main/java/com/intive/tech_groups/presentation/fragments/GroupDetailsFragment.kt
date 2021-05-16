package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.intive.tech_groups.presentation.Stage
import com.intive.tech_groups.presentation.screens.GroupDetailsScreen
import com.intive.tech_groups.presentation.screens.MainScreen
import com.intive.tech_groups.presentation.viewmodels.StageViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GroupDetailsFragment : Fragment(){

    private val stageViewModel by sharedViewModel<StageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            val stageList = listOf(
                Stage("0","Etap I", "01.01-28.02.2021", "zakończony"),
                Stage("1","Etap II", "01.03-31.04.2021", "w trakcie"),
                Stage("2","Etap III", "01.05-31.06.2021", "nieaktywny"),
                Stage("3","Etap IV", "01.07-31.08.2021", "nieaktywny")
            )

            setContent {
                PatronativeTheme {
                    GroupDetailsScreen(
                        stageList,
                        stageViewModel,
                        findNavController()
                    )
                }
            }
        }
    }
}