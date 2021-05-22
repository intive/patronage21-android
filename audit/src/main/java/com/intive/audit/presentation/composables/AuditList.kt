package com.intive.audit.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.paging.compose.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.intive.repository.domain.model.Audit
import com.intive.ui.components.ErrorItem
import com.intive.ui.components.LoadingItem
import com.intive.ui.components.LoadingView
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AuditsList(
    modifier: Modifier = Modifier,
    audits: LazyPagingItems<Audit>,
    query: String,
    onQueryChanged: (String) -> Unit,
    showSearchField: Boolean,
    showFilterField: Boolean,
    onSearchIconClick: (Boolean) -> Unit,
    onFilterIconClick: (Boolean) -> Unit,
    onSortByChanged: (String) -> Unit
) {
    Column(modifier = modifier) {

        val listState = rememberLazyListState()

        val coroutineScope = rememberCoroutineScope()

        val showUpButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        AuditListHeader(
            query = query,
            onQueryChanged = onQueryChanged,
            showSearchField = showSearchField,
            showFilterField = showFilterField,
            onSearchIconClick = onSearchIconClick,
            onFilterIconClick = onFilterIconClick,
            showUpButton = showUpButton,
            onUpButtonClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(index = 0)
                }
            },
            onSortByChanged = onSortByChanged
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                state = listState,
            ) {
                items(audits) { audit ->
                    Row(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    ) {
                        Audit(
                            audit = audit!!
                        )
                    }
                    Divider(
                        color = Color.LightGray,
                        thickness = 0.5.dp
                    )
                }

                audits.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingView(modifier = Modifier.fillParentMaxWidth()) }
                        }
                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = audits.loadState.refresh as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    modifier = Modifier.fillParentMaxWidth(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = audits.loadState.append as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}