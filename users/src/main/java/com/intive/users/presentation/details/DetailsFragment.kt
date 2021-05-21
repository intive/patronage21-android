package com.intive.users.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.intive.ui.PatronativeTheme
import com.intive.users.presentation.composables.screens.DetailsScreen
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.Intent
import android.net.Uri


class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel<DetailsViewModel>()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mock = viewModel.user
        mock.firstName = args.login
        val projects = viewModel.projects

        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    DetailsScreen(
                        navController = navController,
                        user = mock,
                        projects = projects,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userContactEvent.collect { event ->
                when(event) {
                    is DetailsViewModel.UserContactEvent.DialPhoneNumber -> {
                        val phoneNumber = event.phoneNumber

                        val intent =
                            Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                        startActivity(intent)
                    }
                    is DetailsViewModel.UserContactEvent.LaunchWebsite -> {
                        var websiteUrl = event.websiteUrl
                        if (!websiteUrl.startsWith("http://") && !websiteUrl.startsWith("https://"))
                            websiteUrl = "http://$websiteUrl"

                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                        startActivity(intent)
                    }
                    is DetailsViewModel.UserContactEvent.SendEmail -> {
                        val email = event.email

                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
                        startActivity(intent)
                    }
                }
            }
        }
    }
}