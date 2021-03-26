package com.example.patron_a_tive.users_module.users_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.patron_a_tive.R
import com.example.patron_a_tive.findNavController
import com.example.patron_a_tive.users_module.components.UsersScreen

data class Person(val firstName: String, val lastName: String)

class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                Column {
                    MaterialTheme {
                        UsersScreen(
                            users = viewModel.users,
                            onExecuteSearch = {},
                            onQueryChanged = {},
                            query = "",
                            onItemClick = {
                            })
                    }
                }
            }
        }
    }
}