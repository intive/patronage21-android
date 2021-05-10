package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.intive.repository.util.Resource
import com.intive.ui.components.*
import com.intive.users.R
import com.intive.users.presentation.composables.UserListItem
import com.intive.users.presentation.composables.ScreenInfo
import com.intive.users.presentation.composables.Search
import com.intive.users.presentation.users.UsersViewModel
import com.intive.ui.components.Spinner
import com.intive.ui.components.UsersHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi

@Composable
fun UsersScreen(
    viewModel: UsersViewModel,
    navController: NavController
) {

    val candidates = viewModel.candidates.collectAsLazyPagingItems()
    val leaders = viewModel.leaders.collectAsLazyPagingItems()
    val techGroups = viewModel.techGroups.value
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
                IntroSection(
                    title = stringResource(R.string.users),
                    text = stringResource(R.string.lorem_placeholder)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Search(
                    query = query.value,
                    onQueryChanged = {
                        viewModel.onQueryChanged(it)
                    },
                    onExecuteSearch = {

                    }
                )
                Spacer(modifier = Modifier.padding(16.dp))

                when (techGroups) {
                    is Resource.Success -> {
                        Spinner(
                            items = techGroups.data!!
                        ) { group ->
                            viewModel.onTechGroupsChanged(group)
                        }
                    }
                    is Resource.Error -> ErrorItem(
                        message = stringResource(id = R.string.an_error_occurred),
                    ) {
                        viewModel.onTechGroupsRetryClicked()
                    }
                    is Resource.Loading -> {
                        Box {
                            Spinner(listOf("")) {}
                            LoadingItem()
                        }
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
                    text = stringResource(id = R.string.leaders),
                    count = when (viewModel.totalLeaders.value) {
                        is Resource.Loading -> 0
                        is Resource.Error -> 0
                        is Resource.Success -> viewModel.totalLeaders.value.data
                    },
                    showCount = true,
                )
            }
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
                            message = stringResource(id = R.string.an_error_occurred),
                            modifier = Modifier.fillParentMaxWidth(),
                            onClickRetry = {
                                retry()
                                viewModel.onLeadersRetryClicked()
                            }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = leaders.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = stringResource(id = R.string.an_error_occurred),
                            onClickRetry = {
                                retry()
                                viewModel.onLeadersRetryClicked()
                            }
                        )
                    }
                }
                loadState.refresh is LoadState.NotLoading && loadState.refresh !is LoadState.Error -> {
                    if(leaders.itemCount == 0) {
                        item{
                            EmptyItem()
                        }
                    } else {
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
                    count = when (viewModel.totalCandidates.value) {
                        is Resource.Loading -> 0
                        is Resource.Error -> 0
                        is Resource.Success -> viewModel.totalCandidates.value.data
                    },

                    showCount = true,
                )
            }

        }

        candidates.apply {
            when {
                loadState.source.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxWidth()) }
                }
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
                            message = stringResource(id = R.string.an_error_occurred),
                            modifier = Modifier.fillParentMaxWidth(),
                            onClickRetry = {
                                retry()
                                viewModel.onCandidatesRetryClicked()
                            }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = candidates.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = stringResource(id = R.string.an_error_occurred),
                            onClickRetry = {
                                retry()
                                viewModel.onCandidatesRetryClicked()
                            }
                        )
                    }
                }
                loadState.refresh is LoadState.NotLoading && loadState.refresh !is LoadState.Error -> {
                    if(candidates.itemCount == 0) {
                        item{
                            EmptyItem()
                        }
                    } else {
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
                    }
                }
            }
        }
    }
}

