package com.intive.users.presentation.deactivate_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.intive.shared.forceRestart
import com.intive.ui.PatronativeTheme
import com.intive.users.R
import com.intive.users.presentation.composables.screens.DeactivateUserScreen
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeactivateUserFragment : Fragment() {

    private val viewModel by viewModel<DeactivateUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    DeactivateUserScreen(
                        viewModel = viewModel,
                        navController = findNavController()
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deactivateUserEvent.collect { event ->
                when(event) {
                    DeactivateUserViewModel.DeactivateUserEvent.NavigateToRegistrationScreen -> {
                        Toast.makeText(requireContext(), getString(R.string.account_was_deactivated), Toast.LENGTH_LONG).show()
                        requireActivity().forceRestart()
                    }
                    DeactivateUserViewModel.DeactivateUserEvent.ShowErrorMessage -> {
                        Toast.makeText(requireContext(), getString(R.string.an_error_occurred_during_deactivation), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

