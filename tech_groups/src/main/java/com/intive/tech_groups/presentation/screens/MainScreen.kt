package com.intive.tech_groups.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.repository.util.Resource
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.fragments.MainFragmentDirections
import com.intive.tech_groups.presentation.viewmodels.MainViewModel
import com.intive.ui.components.*
import com.intive.ui.components.BoxButton
import com.intive.ui.components.Spinner
import com.intive.ui.components.TitleText

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val filteredList by viewModel.filteredList.observeAsState()
    val filters = viewModel.filters.value
    val groups = viewModel.groups.value

    LayoutContainer{
        FABLayout(
            onClick = { navController.navigate(R.id.action_add_group) },
            contentDescription = stringResource(R.string.add_new_technology_group)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
            ) {
                TitleText(
                    text = stringResource(R.string.tech_groups_title),
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                )
                when (filters) {
                    is Resource.Success -> {
                        GroupSpinner(
                            items = filters.data!!
                        ) {
                            viewModel.filterList(it.queryValue)
                        }
                    }
                    is Resource.Error -> ErrorItem(
                        message = stringResource(id = R.string.an_error_occurred)
                    ) {
                        viewModel.getFilters()
                    }
                    is Resource.Loading -> {
                        Box {
                            Spinner(listOf("")) {}
                            LoadingItem()
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (groups) {
                        is Resource.Success -> {
                            var index = 0
                            while (!filteredList.isNullOrEmpty() && index < filteredList!!.size) {
                                Row {
                                    Column(Modifier.weight(1f)) {
                                        BoxButton(
                                            text = filteredList!![index].name,
                                            onClick = {
                                                navController.navigate(
                                                    MainFragmentDirections.actionMainFragmentToGroupDetailsFragment()
                                                )
                                            },
                                            contentOnTop = false
                                        ) {
                                            Text(stringResource(R.string.technologies))
                                            for (technology in filteredList!![index].technologies) {
                                                Text(technology)
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.size(20.dp))
                                    Column(Modifier.weight(1f)) {
                                        if (index + 1 < filteredList!!.size) {
                                            BoxButton(
                                                text = filteredList!![index + 1].name,
                                                onClick = {
                                                    navController.navigate(
                                                        MainFragmentDirections.actionMainFragmentToGroupDetailsFragment()
                                                    )
                                                },
                                                contentOnTop = false
                                            ) {
                                                Text(stringResource(R.string.technologies))
                                                for (technology in filteredList!![index + 1].technologies) {
                                                    Text(technology)
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(20.dp))
                                        index += 2
                                    }
                                }
                            }
                        }
                        is Resource.Error -> ErrorItem(
                            message = stringResource(id = R.string.an_error_occurred)
                        ) {
                            viewModel.getGroups()
                        }
                        is Resource.Loading -> {
                            Box {
                                LoadingItem()
                            }
                        }
                    }
                }
            }
        }
    }
}