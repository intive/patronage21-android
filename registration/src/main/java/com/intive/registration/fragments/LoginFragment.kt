package com.intive.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.intive.registration.screens.LoginScreen
import com.intive.registration.screens.SuccessScreen
import com.intive.registration.viewmodels.LoginViewModel
import com.intive.registration.viewmodels.RegistrationSuccessDialogState
import com.intive.registration.viewmodels.SharedViewModel
import com.intive.shared.forceRestart
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel by viewModel<LoginViewModel>()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    LoginScreen(viewModel, findNavController())
                    if (sharedViewModel.successDialogState == RegistrationSuccessDialogState.SHOW_DIALOG) {
                        SuccessScreen(sharedViewModel)
                        sharedViewModel.successDialogState =
                            RegistrationSuccessDialogState.HIDE_DIALOG
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.shouldRestartActivity.observe(viewLifecycleOwner, {
            if(it){
                sharedViewModel.shouldRestartActivity.value = false
                requireActivity().forceRestart()
            }
        })
    }
}