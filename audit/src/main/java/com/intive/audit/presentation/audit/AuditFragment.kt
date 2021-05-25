package com.intive.audit.presentation.audit

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.intive.audit.presentation.composables.AuditsList
import com.intive.ui.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuditFragment : Fragment() {

    private val viewModel by viewModel<AuditViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val audits = viewModel.audits.collectAsLazyPagingItems()

                val query = viewModel.query

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
                            query = query.value,
                            onQueryChanged = viewModel::onQueryChanged,
                            showSearchField = showSearchField,
                            showFilterField = showFilterField,
                            onSearchIconClick = viewModel::onSearchIconClick,
                            onFilterIconClick = viewModel::onFilterIconClick,
                            onSortByChanged = viewModel::onSortByChanged
                        )
                    }
                }
            }
        }
    }
}

