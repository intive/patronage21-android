package com.intive.audit.presentation.audit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.audit.presentation.composables.screens.AuditScreen

import com.intive.ui.*

class AuditFragment : Fragment() {

    private val viewModel: AuditViewModel by viewModels()

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    AuditScreen(
                        auditViewModel = viewModel
                    )
                }
            }
        }
    }
}

