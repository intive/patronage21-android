package com.intive.gradebook.composables.screens

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
import com.intive.ui.components.Spinner

@Composable
fun GradebookScreen(
    viewModel: GradebookViewModel,
    navController: NavController
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val setting = remember { mutableStateOf("") }
    val participants = viewModel.participants.collectAsLazyPagingItems()
    val query = viewModel.query
    val lazyListState = rememberLazyListState()
    Column {
        val modifier = Modifier.padding(
            start = 30.dp,
            end = 30.dp,
            bottom = 8.dp,
            top = 16.dp
        )
        LazyColumn (
            state=lazyListState
        ) {
            item {
                Column(
                    modifier = modifier
                ) {
                    ScreenInfo()
                    Spacer(modifier = Modifier.padding(16.dp))
                    Spinner(
                        //na razie grupy na sztywno, podciągnę je z api w następnym sprincie
                        items = stringArrayResource(id = R.array.groups_spinner).asList()
                    ) {

                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Spinner(
                        //sortowanie chwilowo nie działa, zrobię je w następnym sprincie
                        items = stringArrayResource(id = R.array.sort_spinner).asList()
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

            items(participants) { person ->
                GradebookListItem(gradebook = person!!, onItemClick = { val bundle=bundleOf(
                    "firstName" to person.firstName,
                    "lastName" to person.lastName,
                    "gradeNames" to person.gradeNames,
                    "grades" to person.grades,
                    "gradeReviews" to person.gradeReviews,
                    "averageGrade" to person.averageGrade)
                    navController.navigate(R.id.action_gradebookFragment_to_gradesFragment, bundle)
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
                                message = e.error.localizedMessage!!,
                                modifier = Modifier.fillParentMaxWidth(),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = participants.loadState.append as LoadState.Error
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
    Row(
        modifier = Modifier
            .padding(end = 40.dp, bottom = 100.dp)
            .size(64.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = Color.White,
            onClick = { setShowDialog(true) }
        ) {
            Icon(Icons.Filled.Add, stringResource(id = R.string.select_data))
        }
    }
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
        println(message)
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