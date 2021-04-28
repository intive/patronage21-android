package com.intive.users.presentation.composables.screens

import android.util.Log
import android.widget.ProgressBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.intive.repository.domain.model.User
import com.intive.users.R
import com.intive.users.presentation.composables.UserListItem
import com.intive.users.presentation.composables.ScreenInfo
import com.intive.users.presentation.composables.Search
import com.intive.users.presentation.users.UsersViewModel
import com.intive.ui.components.Spinner
import com.intive.ui.components.UsersHeader

@Composable
fun UsersScreen(
    viewModel: UsersViewModel,
    navController: NavController
) {

    val candidates = viewModel.candidates.collectAsLazyPagingItems()
    val leaders = viewModel.leaders.collectAsLazyPagingItems()
    val query = viewModel.query

    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState
    ) {
        item {
            Column(
                modifier = Modifier.padding(
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 8.dp,
                    top = 16.dp
                )
            ) {
                ScreenInfo()
                Spacer(modifier = Modifier.padding(16.dp))
                Search(
                    query = query.value,
                    onQueryChanged = {
                        viewModel.onQueryChanged(it)
                    },
                    onExecuteSearch = {}
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Spinner(
                    items = listOf(
                        "Wszystkie grupy",
                        "Java",
                        "QA",
                        "Android",
                        "JavaScript",
                    )
                ) {
                }
            }
        }


        item {
            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                UsersHeader(
                    text = stringResource(id = R.string.leaders),
                    count = leaders.itemCount,
                    showCount = true,
                )
            }
        }

        items(leaders) { user ->
            UserListItem(user = user!!, onItemClick = {
                navController.navigate(R.id.action_usersFragment_to_detailsFragment)
            })
            Divider(
                color = Color(0xFFF1F1F1),
                thickness = 2.dp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }

        leaders.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxWidth()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = leaders.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxWidth(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = leaders.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                UsersHeader(
                    text = stringResource(id = R.string.participants),
                    count = candidates.itemCount,
                    showCount = true,
                )
            }

        }

        items(candidates) { user ->
            UserListItem(user = user!!, onItemClick = {
                navController.navigate(R.id.action_usersFragment_to_detailsFragment)
            })
            Divider(
                color = Color(0xFFF1F1F1),
                thickness = 2.dp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }

        candidates.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxWidth()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = candidates.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxWidth(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = candidates.loadState.append as LoadState.Error
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

@Composable
fun LoadingView(modifier: Modifier) {
    Box(modifier = modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            color = Color.Red
        )
        Button(onClick = onClickRetry) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}