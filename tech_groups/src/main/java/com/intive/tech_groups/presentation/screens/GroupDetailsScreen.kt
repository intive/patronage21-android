package com.intive.tech_groups.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.repository.domain.model.GroupParcelable
import com.intive.repository.domain.model.Stage
import com.intive.repository.util.Resource
import com.intive.tech_groups.R

import com.intive.ui.components.*

@Composable
fun GroupDetailsScreen(
    group: GroupParcelable,
    stageList: Resource<List<Stage>>?,
    getStageDetails: (Long) -> Unit,
    onGetStagesRetryClick: (String) -> Unit,
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val listState = rememberLazyListState()

        LayoutContainer(
            bottomPadding = 0.dp
        ) {
            LazyColumn(
                state = listState,
            ) {
                item {
                    Column {
                        TitleText(
                            text = group.name,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                        )
                        Text(
                            text = "Technologie:",
                            style = MaterialTheme.typography.h6
                        )
                        for(tech in group.technologies){
                            Text(text = "- $tech")
                        }
                    }
                    SectionHeader(
                        modifier = Modifier.padding(
                            top = 15.dp,
                            bottom = 15.dp
                        ),
                        title = {
                            SectionHeaderText(text = stringResource(id = R.string.description))
                        }
                    )

                    Column {
                        Text(
                            text = group.description
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (stageList) {
                                is Resource.Success -> {
                                    var index = 0
                                    while (!stageList.data.isNullOrEmpty() && index < stageList.data!!.size) {
                                        val indexCloned = index
                                        Row {
                                            StageBoxButton(
                                                modifier = Modifier.weight(1f),
                                                name = stageList.data!![index].name,
                                                dateBegin = stageList.data!![index].dateBegin,
                                                dateEnd = stageList.data!![index].dateEnd,
                                                state = stageList.data!![index].state
                                            ) {
                                                getStageDetails(stageList.data!![indexCloned].id.toLong())
                                                navController?.navigate(R.id.action_groupDetailsFragment_to_stageFragment)
                                            }
                                            Spacer(modifier = Modifier.size(20.dp))
                                            StageBoxButton(
                                                modifier = Modifier.weight(1f),
                                                name = stageList.data!![index + 1].name,
                                                dateBegin = stageList.data!![index + 1].dateBegin,
                                                dateEnd = stageList.data!![index + 1].dateEnd,
                                                state = stageList.data!![index + 1].state
                                            ) {
                                                getStageDetails(stageList.data!![indexCloned + 1].id.toLong())
                                                navController?.navigate(R.id.action_groupDetailsFragment_to_stageFragment)
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(20.dp))
                                        index += 2
                                    }
                                }
                                is Resource.Error -> ErrorItem(
                                    message = stringResource(id = R.string.an_error_occurred),
                                ) {
                                    onGetStagesRetryClick(group.id)
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

                item {
                    HeaderWithCount(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        text = stringResource(id = R.string.leaders),
                        count = 0,
                        showCount = true,
                    )
                }

                item {
                    HeaderWithCount(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        text = stringResource(id = R.string.candidates),
                        count = 0,
                        showCount = true,
                    )
                }

                item {
                    PrimaryButton(
                        paddingTop = dimensionResource(id = R.dimen.screen_padding_small),
                        text = stringResource(id = R.string.resign_from_candidacy),
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun StageBoxButton(
    modifier: Modifier,
    name: String,
    dateBegin: String,
    dateEnd: String,
    state: String,
    onClick: () -> Unit
) {
    Column(modifier) {
        BoxButton(
            text = name,
            onClick = onClick,
            contentOnTop = false
        ) {
            Row{
                val textStyleBody1 = MaterialTheme.typography.body1
                val (textStyle, updateTextStyle) = remember { mutableStateOf(textStyleBody1) }
                Text(
                    text = " ${dateBegin.dropLast(5)} - $dateEnd ",
                    style = textStyle,
                    maxLines = 1,
                    softWrap = false,
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.didOverflowWidth) {
                            updateTextStyle(textStyle.copy(fontSize = textStyle.fontSize * 0.9))
                        }
                    }
                )
            }
            Text(text = state)
        }
    }
}