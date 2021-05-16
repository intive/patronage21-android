package com.intive.users.presentation.deactivate_user

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.ui.PatronativeTheme
import com.intive.users.R
import com.intive.users.presentation.composables.screens.DeactivateUserScreen
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeactivateUserFragment : Fragment() {
    private val viewModel by viewModel<DeactivateUserViewModel>()
    private val args: DeactivateUserFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getUser(args.login)

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
        val navController = findNavController()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deactivateUserEvent.collect { event ->
                when(event) {
                    DeactivateUserViewModel.DeactivateUserEvent.NavigateToRegistrationScreen -> {
                        Toast.makeText(requireContext(), getString(R.string.account_was_deactivated), Toast.LENGTH_LONG).show()
                    }
                    DeactivateUserViewModel.DeactivateUserEvent.ShowErrorMessage -> {
                        Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

