package com.intive.gradebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.gradebook.composables.screens.GradebookScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Person(val firstName: String, val lastName: String)

class GradebookFragment : Fragment() {

    private val viewModel: GradebookViewModel by viewModels()
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