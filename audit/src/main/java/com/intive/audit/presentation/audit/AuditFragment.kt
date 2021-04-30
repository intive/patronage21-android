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
import com.intive.audit.presentation.composables.AuditsList
import com.intive.ui.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.intive.audit.presentation.audit.AuditListEvent.*

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

                //temporary
                //val audits: List<Audit> = List(1000) { Audit(1, "14-04-2021", "Logowanie", "Adam Kowalski") }

                val audits = viewModel.audits.value

                val query = viewModel.query.value

                val showSearchField = viewModel.showSearchField.value

                val showFilterField = viewModel.showFilterField.value

                val page = viewModel.page.value

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
                            onChangeAuditScrollPosition = viewModel::onChangeAuditScrollPosition,
                            onQueryChanged = viewModel::onQueryChanged,
                            showSearchField = showSearchField,
                            showFilterField = showFilterField,
                            onSearchIconClick = viewModel::onSearchIconClick,
                            onFilterIconClick = viewModel::onFilterIconClick,
                            onExecuteSearch = { viewModel.onTriggerEvent(NewSearchEvent) },
                            page = page,
                            onNextPage = {
                                viewModel.onTriggerEvent(NextPageEvent)
                            }
                            )
                    }
                }
            }
        }
    }
}

