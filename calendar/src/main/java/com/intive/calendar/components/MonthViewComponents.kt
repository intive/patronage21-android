package com.intive.calendar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.repository.domain.model.Day


@ExperimentalFoundationApi
@Composable
fun MonthView(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel
) {
    val showWeekView by calendarViewModel.showWeekView.observeAsState()
    val header by calendarViewModel.monthHeader.observeAsState()

    if (showWeekView == false) {
        CalendarHeader(
            header!!,
            { calendarViewModel.goToPreviousMonth() },
            { calendarViewModel.goToNextMonth() })
        CalendarGrid(navController, calendarViewModel)
    }
}

@ExperimentalFoundationApi
@Composable
fun CalendarGrid(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel
) {

    val currentMonth by calendarViewModel.currentMonth.observeAsState()
    val items = currentMonth?.toList()

    LazyVerticalGrid(
        cells = GridCells.Fixed(7)
    ) {
        if (items != null) {
            items(items.size) {
                if (items[it] is Day) {
                    var bgColor = Color.White
                    var onClick = {}
                    if ((items[it] as Day).events.isNotEmpty()) {

                        bgColor = colorResource(R.color.pale_blue)

                        if ((items[it] as Day).events.size == 1) {
                            val bundle = bundleOf(
                                "date" to "${(items[it] as Day).index}.04.2021",
                                "time" to (items[it] as Day).events[0].time,
                                "name" to (items[it] as Day).events[0].name
                            )
                            onClick =
                                {
                                    navController.navigate(
                                        R.id.action_calendarFragment_to_eventFragment,
                                        bundle
                                    )
                                }
                        } else if ((items[it] as Day).events.size > 1) {
                            val bundle = bundleOf(
                                "header" to "${(items[it] as Day).index}${stringResource(R.string.mock_date)}",
                                "events" to Gson().toJson((items[it] as Day).events)
                            )
                            onClick =
                                {
                                    navController.navigate(
                                        R.id.action_calendarFragment_to_dayFragment,
                                        bundle
                                    )
                                }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(bgColor)
                            .clickable(onClick = onClick)
                    ) {
                        Text(
                            text = (items[it] as Day).index.toString(),
                            style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = items[it].toString(),
                            style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

            }
        }
    }
}