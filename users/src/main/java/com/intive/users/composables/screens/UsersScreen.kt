package com.intive.users.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.users.R
import com.intive.users.UsersViewModel

@Composable
fun UsersScreen(
    viewModel: UsersViewModel,
    navController: NavController
) {
    Column {
        val users = viewModel.users
        val query = viewModel.query.collectAsState()


        val modifier = Modifier.padding(
            start = 30.dp,
            end = 30.dp,
            bottom = 8.dp,
            top = 16.dp
        )
        LazyColumn {
            item {
                Column(
                    modifier = modifier
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
                    Header(
                        text = stringResource(id = R.string.leaders),
                        count = users.size,
                        showCount = true,
                    )
                }
            }

            items(users) { person ->
                PersonListItem(person = person, onItemClick = {
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
                    Header(
                        text = stringResource(id = R.string.participants),
                        count = users.size,
                        showCount = true,
                    )
                }

            }

            items(users) { person ->
                PersonListItem(person = person, onItemClick = {
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