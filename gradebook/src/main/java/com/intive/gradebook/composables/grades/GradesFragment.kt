package com.intive.gradebook.composables.grades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.gradebook.composables.screens.GradesScreen

@ExperimentalComposeUiApi
class GradesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val firstName = requireArguments().getString("firstName")
        val lastName = requireArguments().getString("lastName")
        val userName = requireArguments().getString("userName")
        val group = requireArguments().getString("group")
        val gradeNames = requireArguments().getStringArray("gradeNames")
        val grades = requireArguments().getFloatArray("grades")
        val gradeReviews = requireArguments().getStringArray("gradeReviews")
        val averageGrade = requireArguments().getFloat("averageGrade")
        val viewModel: GradesViewModel by viewModels { GradesViewModelFactory(
            firstName!!,
            lastName!!,
            userName!!,
            group!!,
            gradeNames!!,
            grades!!,
            gradeReviews!!,
            averageGrade
        ) }
        val user = viewModel.user

        return ComposeView(requireContext()).apply {
            setContent {
                val scrollState = rememberScrollState()
                PatronativeTheme {
                    Column(
                        Modifier.verticalScroll(scrollState)
                    ) {
                        GradesScreen(
                            user = user
                        )
                    }
                }
            }
        }
    }
}