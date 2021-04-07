package com.intive.users.edit_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.DetailsViewModel
import com.intive.users.Person
import com.intive.users.composables.screens.EditUserScreen

class EditUserFragment : Fragment() {

    private val viewModel: DetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mock = viewModel.user.copy()
        val projects = viewModel.projects

        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        EditUserScreen(
                            navController = navController,
                            user = mock,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}