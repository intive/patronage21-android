package com.intive.audit.presentation.audit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.audit.domain.Audit
import com.intive.audit.presentation.composables.AuditsList
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

                //temporary
                val audits: List<Audit> = List(1000) { Audit(1, "14-04-2021", "Logowanie", "Adam Kowalski") }

                val query = viewModel.query.value

                val showSearchField = viewModel.showSearchField.value

                val showFilterField = viewModel.showFilterField.value

                PatronativeTheme {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AuditsList(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp),
                            audits = audits,
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            showSearchField = showSearchField,
                            showFilterField = showFilterField,
                            onSearchIconClick = viewModel::onSearchIconClick,
                            onFilterIconClick = viewModel::onFilterIconClick
                        )
                    }
                }
            }
        }
    }
}

