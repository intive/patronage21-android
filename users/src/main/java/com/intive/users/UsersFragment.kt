package com.intive.users

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.intive.ui.PatronativeTheme
import com.intive.users.composables.screens.UsersScreen

enum class Gender {
    MALE, FEMALE, DIFFERENT
}

data class Person(
    var gender: Gender,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String
    )

class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    UsersScreen(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}