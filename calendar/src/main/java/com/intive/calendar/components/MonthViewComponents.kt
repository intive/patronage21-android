package com.intive.calendar.components

import android.util.Log
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
import com.intive.calendar.utils.getDateString
import com.intive.calendar.utils.weekDaysCalendarClass
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.repository.domain.model.Day
import java.util.*


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
    val monthEvents by calendarViewModel.monthEvents.observeAsState()
    val items = currentMonth!!.toList()


    LazyVerticalGrid(
        cells = GridCells.Fixed(7)
    ) {

        items(items.size) {

            if (items[it] is Calendar) {

                var bgColor = Color.White
                var onClick = {}

                if (monthEvents!!.find{ it1 -> it1.date == getDateString(items[it] as Calendar)} != null) {

                    val index = monthEvents!!.indexOfFirst { it2 -> it2.date!! == getDateString(items[it] as Calendar) }

                    bgColor = colorResource(R.color.pale_blue)

                    if (monthEvents!![index].events!!.size == 1) {
                        val bundle = bundleOf(
                            "date" to "${weekDaysCalendarClass[(items[it] as Calendar)[Calendar.DAY_OF_WEEK]]}, ${getDateString((items[it] as Calendar), ".")}",
                            "time" to "${monthEvents!![index].events!![0].timeStart} - ${monthEvents!![index].events!![0].timeEnd}",
                            "name" to monthEvents!![index].events!![0].name
                        )
                        onClick =
                            {
                                navController.navigate(
                                    R.id.action_calendarFragment_to_eventFragment,
                                    bundle
                                )
                            }
                    } else if (monthEvents!![index].events!!.size > 1) {
                        val bundle = bundleOf(
                            "header" to "${weekDaysCalendarClass[(items[it] as Calendar)[Calendar.DAY_OF_WEEK]]}, ${getDateString((items[it] as Calendar), ".")}",
                            "events" to Gson().toJson(monthEvents!![index].events!!)
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
                        text = (items[it] as Calendar)[Calendar.DAY_OF_MONTH].toString(),
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