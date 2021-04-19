package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
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

    val users = viewModel.getCandidatesPagination().collectAsLazyPagingItems()
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
                    count = users.itemCount,
                    showCount = true,
                )
            }
        }

        items(users) { user ->
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
                    count = users.itemCount,
                    showCount = true,
                )
            }

        }

        items(users) { user ->
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