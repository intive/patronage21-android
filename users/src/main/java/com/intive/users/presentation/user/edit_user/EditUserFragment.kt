package com.intive.users.presentation.user.edit_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.repository.domain.model.User
import com.intive.users.presentation.composables.screens.EditUserScreen

class EditUserFragment : Fragment() {

    private val viewModel: EditUserViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mock = User(
            "",
            "Jan",
            "Kowalski",
            "jkowalski",
            "Mężczyzna",
            emptyList(),
            "jankowalski@gmal.com",
            "123456789",
            "github.com/KowalskiJan",
            "Jestem programista",
            "Candidate"
        )

        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EditUserScreen(
                        navController = navController,
                        user = mock,
                    )
                }
            }
        }
    }
}