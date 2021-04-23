package com.intive.audit.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.audit.R
import com.intive.audit.presentation.audit.AuditListEvent
import com.intive.audit.presentation.audit.PAGE_SIZE
import com.intive.repository.domain.model.Audit
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AuditsList(
        modifier: Modifier = Modifier,
        audits: List<Audit>,
        onChangeAuditScrollPosition: (Int) -> Unit,
        query: String,
        onQueryChanged: (String) -> Unit,
        showSearchField: Boolean,
        showFilterField: Boolean,
        onSearchIconClick: (Boolean) -> Unit,
        onFilterIconClick: (Boolean) -> Unit,
        onExecuteSearch: () -> Unit,
        page: Int,
        onNextPage: (AuditListEvent) -> Unit,
) {
    Column(modifier = modifier) {
        AuditListHeader(
            query = query,
            onQueryChanged = onQueryChanged,
            showSearchField = showSearchField,
            showFilterField = showFilterField,
            onSearchIconClick = onSearchIconClick,
            onFilterIconClick = onFilterIconClick,
            onExecuteSearch = onExecuteSearch
        )

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                state = listState,
            ) {
                itemsIndexed(items = audits) { index, audit ->
                    onChangeAuditScrollPosition(index)
                    if((index + 1) >= (page * PAGE_SIZE)){
                        onNextPage(AuditListEvent.NextPageEvent)
                    }
                    Row (
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    ){
                        Audit(
                            audit = audit
                        )
                    }
                    Divider(
                        color = Color.LightGray,
                        thickness = 0.5.dp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .align(Alignment.BottomEnd),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = showButton
                ) {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = 0)
                            }
                        },
                        elevation = FloatingActionButtonDefaults.elevation(
                            pressedElevation = 8.dp, defaultElevation = 2.dp
                        )
                    ) {
                        Icon(
                            Icons.Outlined.ArrowUpward,
                            contentDescription = stringResource(R.string.arrow_upward_icon_desc)
                        )
                    }
                }
            }
        }
    }
}