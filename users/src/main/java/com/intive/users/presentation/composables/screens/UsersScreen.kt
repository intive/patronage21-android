package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.intive.repository.domain.model.User
import com.intive.repository.util.Resource
import com.intive.ui.components.*
import com.intive.users.R
import com.intive.users.presentation.composables.Search
import com.intive.users.presentation.users.UsersViewModel
import com.intive.ui.components.Spinner
import com.intive.ui.components.GroupSpinner
import com.intive.users.presentation.users.STATUS_ACTIVE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi

@Composable
fun UsersScreen(
    viewModel: UsersViewModel,
    navController: NavController
) {

    val candidates = viewModel.candidates.collectAsState(initial = Resource.Loading())
    val leaders = viewModel.leaders.collectAsState(initial = Resource.Loading())
    val techGroups = viewModel.techGroups.value
    val query = viewModel.query

    val lazyListState = rememberLazyListState()
    LayoutContainer {
        LazyColumn(
            state = lazyListState
        ) {
            item {
                Column(
                    modifier = Modifier.padding(
                    )
                ) {
                    IntroSection(
                        title = stringResource(R.string.users),
                        text = stringResource(R.string.lorem_placeholder)
                    )

                    Search(
                        query = query.value,
                        onQueryChanged = {
                            viewModel.onQueryChanged(it)
                        },
                        onExecuteSearch = {
                        }
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    when (techGroups) {
                        is Resource.Success -> {
                            GroupSpinner(
                                items = techGroups.data!!
                            ) { group ->
                                viewModel.onTechGroupsChanged(group.queryValue)
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
                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }


            item {
                Column {
                    HeaderWithCount(
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

            when (leaders.value) {
                is Resource.Error -> item { ErrorItem(message = "Błąd", onClickRetry = {}) }
                is Resource.Loading -> item { LoadingItem() }
                is Resource.Success -> {
                    if (leaders.value.data!!.isEmpty()) {
                        item {
                            EmptyItem()
                        }
                    }
                    items(leaders.value.data!!) { user ->
                        PersonListItem(
                            user = User(
                                login = user.login,
                                firstName = user.firstName,
                                lastName = user.lastName,
                                image = user.image,
                                phoneNumber = "",
                                projects = emptyList(),
                                email = "",
                                github = "",
                                bio = "",
                                role = "",
                                gender = ""
                            ),
                            onItemClick = {
                                val bundle = bundleOf("login" to it.login)
                                navController.navigate(
                                    R.id.action_usersFragment_to_detailsFragment,
                                    bundle
                                )
                            },
                            rowPadding = 0.dp,
                            additionalText = stringResource(R.string.you),
                            showAdditionalText = viewModel.isLoggedInUser(user.login)
                        )
                        Divider()
                    }
                }
            }


            item {
                Column {
                    HeaderWithCount(
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

            when (candidates.value) {
                is Resource.Error -> item { ErrorItem(message = "Błąd", onClickRetry = {}) }
                is Resource.Loading -> item { LoadingItem() }
                is Resource.Success -> {
                    if (candidates.value.data!!.isEmpty()) {
                        item {
                            EmptyItem()
                        }
                    }
                    items(candidates.value.data!!) { user ->
                        PersonListItem(
                            user = User(
                                login = user.login,
                                firstName = user.firstName,
                                lastName = user.lastName,
                                image = user.image,
                                phoneNumber = "",
                                projects = emptyList(),
                                email = "",
                                github = "",
                                bio = "",
                                role = "",
                                gender = ""
                            ),
                            onItemClick = {
                                val bundle = bundleOf("login" to it.login)
                                navController.navigate(
                                    R.id.action_usersFragment_to_detailsFragment,
                                    bundle
                                )
                            },
                            rowPadding = 0.dp,
                            additionalText = stringResource(R.string.you),
                            showAdditionalText = viewModel.isLoggedInUser(user.login)
                        )
                        Divider()
                    }
                }
            }
        }
    }

}

