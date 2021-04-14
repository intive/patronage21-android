package com.intive.gradebook.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.gradebook.R
import com.intive.gradebook.GradebookViewModel
import com.intive.gradebook.composables.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GradebookScreen(
    viewModel: GradebookViewModel,
    navController: NavController
) {
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    val setting = remember { mutableStateOf("") }
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
                    GroupsSpinner(
                        groups = listOf(
                            "Wszystkie grupy",
                            "Java",
                            "QA",
                            "Android",
                            "JavaScript"
                        )) {

                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    SortSpinner(
                        groups = listOf(
                            "Od najwyższych ocen",
                            "Od najniższych ocen",
                            "Alfabetycznie"
                        )) {

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
                    Header(text = stringResource(id = R.string.participants),
                        text2 = stringResource(id = R.string.average_grade),
                        text3 = setting.value,
                        showText2 = true,
                        showText3 = true,
                        fraction = 0.50f,
                        fraction2 = 0.60f)
                }

            }

            items(users) { person ->
                PersonListItem(person = person,onItemClick = {
                    navController.navigate(R.id.action_gradebookFragment_to_detailsFragment)
                }, addedColumn = setting.value)
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
    Row(
        modifier = Modifier
            .padding(end = 40.dp, bottom = 100.dp)
            .size(64.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        FloatingActionButton(
            backgroundColor = Color(0xFFC2185A),
            contentColor = Color.White,
            onClick = {setShowDialog(true)}
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }
    var addedColumn="Grupa"
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            text= {
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
                    ColumnSpinner(
                        columns = listOf(
                            "Grupa",
                            "Ostatnia ocena",
                            "Ocena za etap I",
                            "Ocena za etap II",
                            "Ocena za etap III",
                            "Ocena za etap IV",
                            "Ocena za etap V",
                        )
                    ) {
                        addedColumn=it
                    }
                }
            },
            dismissButton = {
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Button(
                        onClick = {
                            setting.value = addedColumn
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC2185A)),
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
                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Button(
                        onClick = {
                            setShowDialog(false)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC2185A)),
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
