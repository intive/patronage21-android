package com.intive.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.registration.R
import com.intive.registration.screens.EmailVerificationScreen
import com.intive.registration.viewmodels.EmailVerificationViewModel
import com.intive.registration.viewmodels.RegistrationViewModel
import com.intive.registration.viewmodels.SharedViewModel
import com.intive.ui.PatronativeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TIME_BETWEEN_CLICKS = 500 //milliseconds

class EmailVerificationFragment : Fragment() {

    private val viewModel: EmailVerificationViewModel by viewModel<EmailVerificationViewModel>()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: EmailVerificationFragmentArgs by navArgs()

    private var lastBackPressed = System.currentTimeMillis()


    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //do not allow user back to registration form
        //if user double click back button -> exit app
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(System.currentTimeMillis()-lastBackPressed<=TIME_BETWEEN_CLICKS) {
                        requireActivity().finish()
                    }
                    else {
                        lastBackPressed = System.currentTimeMillis()
                        Toast.makeText(requireContext(), getString(R.string.exit_app_toast_text), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        with(args) {
            viewModel.email = email
            viewModel.login = login
        }
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    EmailVerificationScreen(viewModel, findNavController(), sharedViewModel)
                }
            }
        }
    }
}
