package com.intive.tech_groups.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.intive.tech_groups.Constants.SUCCESS_KEY
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.screens.MainScreen
import com.intive.tech_groups.presentation.viewmodels.MainViewModel
import com.intive.ui.PatronativeTheme
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel<MainViewModel>()
    private val args: MainFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launchWhenStarted {
            if(args.message == SUCCESS_KEY) {
                Snackbar.make(
                    requireView(),
                    requireContext().getString(R.string.group_created),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    MainScreen(viewModel, findNavController())
                }
            }
        }
    }
}
