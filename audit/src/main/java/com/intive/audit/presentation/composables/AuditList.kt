package com.intive.audit.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.intive.audit.domain.Audit
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AuditsList(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    showSearchField: Boolean,
    showFilterField: Boolean,
    onSearchIconClick: (Boolean) -> Unit,
    onFilterIconClick: (Boolean) -> Unit,
    audits: List<Audit>
) {
    Column(modifier = modifier) {
        AuditListHeader(
            query = query,
            onQueryChanged = onQueryChanged,
            showSearchField = showSearchField,
            showFilterField = showFilterField,
            onSearchIconClick = onSearchIconClick,
            onFilterIconClick = onFilterIconClick
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
                .fillMaxSize()

            ,
            contentAlignment = Alignment.BottomEnd
        ) {
            LazyColumn(
                state = listState,
            ) {
                items(items = audits) { audit ->
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
                    .padding(bottom = 10.dp),
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