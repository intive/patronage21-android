package com.intive.gradebook.composables.screens

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.gradebook.R
import com.intive.gradebook.composables.gradebook.GradebookViewModel
import com.intive.gradebook.composables.*
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.intive.repository.domain.model.Gradebook
import com.intive.repository.util.Resource
import com.intive.ui.components.Spinner
import com.intive.ui.components.FABLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.intive.ui.components.*

@ExperimentalCoroutinesApi
@Composable
fun GradebookScreen(
    viewModel: GradebookViewModel,
    navController: NavController
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val setting = remember { mutableStateOf("") }
    val participants = viewModel.participants.collectAsLazyPagingItems()
    val groups = viewModel.techGroups.value
    val lazyListState = rememberLazyListState()
    Column {
        val modifier = Modifier.padding(
            start = 30.dp,
            end = 30.dp,
            bottom = 8.dp,
            top = 16.dp
        )
        LazyColumn(
            state = lazyListState
        ) {
            item {
                Column(
                    modifier = modifier
                ) {
                    ScreenInfo()
                    Spacer(modifier = Modifier.padding(16.dp))

                    when (groups) {
                        is Resource.Success -> {
                            var items = groups.data!!
                                .toMutableList()
                            items.add(0, "Wszystkie grupy")
                            Spinner(
                                items = items
                            ) { group ->
                                viewModel.onTechGroupsChanged(group)
                            }
                        }
                        is Resource.Error -> com.intive.ui.components.ErrorItem(
                            message = stringResource(id = R.string.error_message),
                        ) {
                            viewModel.onTechGroupsRetryClicked()
                        }
                        is Resource.Loading -> {
                            Box {
                                Spinner(listOf("")) {}
                                com.intive.ui.components.LoadingItem()
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(16.dp))
                    Spinner(
                        items = stringArrayResource(id = R.array.sort_spinner).asList()
                    ) { sort ->
                        viewModel.onSortByChanged(sort)
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
                    GradebookHeader(
                        text_col1 = stringResource(id = R.string.participants),
                        text_col2 = stringResource(id = R.string.average_grade),
                        text_col3 = setting.value,
                        showText2 = true,
                        showText3 = true,
                        fraction = 0.50f,
                        fraction2 = 0.60f
                    )
                }

            }

            participants.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingView(modifier = Modifier.fillParentMaxWidth()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = participants.loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = stringResource(id = R.string.error_message),
                                modifier = Modifier.fillParentMaxWidth(),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = participants.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = stringResource(id = R.string.error_message),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.refresh is LoadState.NotLoading && loadState.refresh !is LoadState.Error -> {
                        items(participants) { person ->
                            GradebookListItem(gradebook = person!!, onItemClick = {
                                val bundle = CreateGradebookUser(person)
                                navController.navigate(
                                    R.id.action_gradebookFragment_to_gradesFragment,
                                    bundle
                                )
                            }, addedColumn = setting.value)
                            Divider(
                                color = Color.LightGray,
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
    FABLayout({ setShowDialog(true) }, stringResource(id = R.string.select_data), { })
    var addedColumn = stringArrayResource(id = R.array.addcolumn_spinner)[0]
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = stringResource(id = R.string.select_data),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spinner(
                        items = stringArrayResource(id = R.array.addcolumn_spinner).asList()
                    ) {
                        addedColumn = it
                    }
                }
            },
            dismissButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            setting.value = addedColumn
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.save_data),
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        )
    }
}

fun CreateGradebookUser(person: Gradebook): Bundle {
    val gradeNames = arrayOfNulls<String>(person.entries.size)
    val grades = FloatArray(person.entries.size)
    val gradeReviews = arrayOfNulls<String>(person.entries.size)
    for (i in 0..person.entries.size - 1) {
        gradeNames[i] = person.entries[i].name
        grades[i] = person.entries[i].grade
        gradeReviews[i] = person.entries[i].review
    }
    val bundle = bundleOf(
        "firstName" to person.firstName,
        "lastName" to person.lastName,
        "userName" to person.userName,
        "group" to person.group,
        "gradeNames" to gradeNames,
        "grades" to grades,
        "gradeReviews" to gradeReviews,
        "averageGrade" to person.averageGrade
    )
    return bundle
}