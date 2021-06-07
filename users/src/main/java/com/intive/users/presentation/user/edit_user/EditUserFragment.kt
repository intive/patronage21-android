package com.intive.users.presentation.user.edit_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.intive.ui.PatronativeTheme
import com.intive.repository.domain.model.User
import com.intive.users.R
import com.intive.users.presentation.composables.screens.EditUserScreen
import com.intive.users.presentation.user.UserViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditUserFragment : Fragment() {

    private val viewModel: UserViewModel by sharedViewModel()

    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EditUserScreen(
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.editUserEvent.collect { event ->
                when(event) {
                    UserViewModel.EditUserEvent.OnSuccessfulEdit -> {
                        val navController = findNavController()

                        Snackbar.make(
                            requireView(),
                            getString(R.string.successfully_updated_user),
                            Snackbar.LENGTH_LONG)
                            .show()

                        navController.popBackStack()
                    }
                    UserViewModel.EditUserEvent.OnFailedEdit -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.failed_to_update_user),
                            Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }
}