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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.repository.domain.model.GroupParcelable
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.Stage
import com.intive.tech_groups.presentation.viewmodels.StageViewModel
import com.intive.ui.components.*
import java.security.acl.Group

@Composable
fun GroupDetailsScreen(
    group: GroupParcelable,
    stageList: List<Stage>,
    getStageDetails: (Long) -> Unit,
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val listState = rememberLazyListState()

        LayoutContainer {
            LazyColumn(
                state = listState,
            ) {
                item {
                    Column {
                        TitleText(
                            text = group.name,
                            modifier = Modifier
                                .padding(top = 15.dp, bottom = 15.dp)
                        )
                        Text(
                            text = "Technologie:",
                            style = MaterialTheme.typography.h6
                        )
                        for(tech in group.techList){
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
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var index = 0
                            while (!stageList.isNullOrEmpty() && index < stageList.size) {
                                val indexCloned = index
                                Row {
                                    StageBoxButton(
                                        modifier = Modifier.weight(1f),
                                        name = stageList[index].name,
                                        timeInterval = stageList[index].timeInterval,
                                        state = stageList[index].state
                                    ) {
                                        getStageDetails(stageList[indexCloned].id.toLong())
                                        navController?.navigate(R.id.action_groupDetailsFragment_to_stageFragment)
                                    }
                                    Spacer(modifier = Modifier.size(20.dp))
                                    StageBoxButton(
                                        modifier = Modifier.weight(1f),
                                        name = stageList[index + 1].name,
                                        timeInterval = stageList[index + 1].timeInterval,
                                        state = stageList[index + 1].state
                                    ) {
                                        getStageDetails(stageList[indexCloned + 1].id.toLong())
                                        navController?.navigate(R.id.action_groupDetailsFragment_to_stageFragment)
                                    }
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
    timeInterval: String,
    state: String,
    onClick: () -> Unit
) {
    Column(modifier) {
        BoxButton(
            text = name,
            onClick = onClick,
            contentOnTop = false
        ) {
            Text(text = timeInterval)
            Text(text = state)
        }
    }
}