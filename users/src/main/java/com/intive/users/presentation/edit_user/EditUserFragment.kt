package com.intive.users.presentation.edit_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.repository.remote.model.UserDTO
import com.intive.users.presentation.composables.screens.EditUserScreen

class EditUserFragment : Fragment() {

    private val viewModel: EditUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mock = UserDTO(
            "Mężczyzna",
            "Jan",
            "Kowalski",
            "jankowalski@gmal.com",
            "123456789",
            "github.com/KowalskiJan",
            "Jestem programista"
        )

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