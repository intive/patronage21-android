package com.intive.tech_groups.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.Stage
import com.intive.ui.components.*

@Preview
@Composable
fun GroupDetailsScreen(

) {
    val stageList = listOf(
        Stage("Etap I", "01.01-28.02.2021", "zakończony"),
        Stage("Etap II", "01.03-31.04.2021", "w trakcie"),
        Stage("Etap III", "01.05-31.06.2021", "nieaktywny"),
        Stage("Etap IV", "01.07-31.08.2021", "nieaktywny")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.screen_padding),
                            end = dimensionResource(id = R.dimen.screen_padding)
                        )
                ) {
                    TitleText(
                        text = "Grupa I",
                        modifier = Modifier
                            .padding(top = 15.dp, bottom = 15.dp)
                    )
                    Text(
                        text = "Technologie:",
                        style = MaterialTheme.typography.h6
                    )
                    Text(text = "Lorem ipsum")
                }
                SectionHeader(
                    modifier = Modifier.padding(
                        top = 15.dp,
                        bottom = 15.dp,
                        start = dimensionResource(id = R.dimen.small_screen_padding),
                        end = dimensionResource(id = R.dimen.small_screen_padding)),
                    title = {
                        SectionHeaderText(text = "Opis")
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.screen_padding),
                            end = dimensionResource(id = R.dimen.screen_padding)
                        )
                ) {
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var index = 0
                        while (!stageList.isNullOrEmpty() && index < stageList!!.size) {
                            Row {
                                StageBoxButton(
                                    modifier = Modifier.weight(1f),
                                    name = stageList!![index].name,
                                    timeInterval = stageList!![index].timeInterval,
                                    state = stageList!![index].state
                                )
                                Spacer(modifier = Modifier.size(20.dp))
                                StageBoxButton(
                                    modifier = Modifier.weight(1f),
                                    name = stageList!![index+1].name,
                                    timeInterval = stageList!![index+1].timeInterval,
                                    state = stageList!![index+1].state
                                )
                            }
                            Spacer(modifier = Modifier.size(20.dp))
                            index += 2
                        }
                    }
                }
            }

            item {
                HeaderWithCount(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = dimensionResource(id = R.dimen.small_screen_padding),
                            end = dimensionResource(id = R.dimen.small_screen_padding)),
                    text = "Liderzy",
                    count = 0,
                    showCount = true,
                )
            }

            item {
                HeaderWithCount(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = dimensionResource(id = R.dimen.small_screen_padding),
                            end = dimensionResource(id = R.dimen.small_screen_padding)),
                    text = "Kandydaci",
                    count = 0,
                    showCount = true,
                )
            }

            item {
                PrimaryButton(
                    paddingTop = dimensionResource(id = R.dimen.small_screen_padding),
                    text = "Zrezygnuj z kandydatury",
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun StageBoxButton(
    modifier: Modifier,
    name: String,
    timeInterval: String,
    state: String
) {
    Column(modifier) {
        BoxButton(
            text = name,
            onClick = { },
            contentOnTop = false
        ) {
            Text(text = timeInterval)
            Text(text = state)
        }
    }
}