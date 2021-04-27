package com.intive.calendar.components

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.*
import java.util.*


@ExperimentalFoundationApi
@Composable
fun MonthView(
    navController: NavController,
    showWeekView: Boolean?,
    headerMonth: String?,
    currentMonth: CurrentMonth?,
    monthEvents: List<Day>?,
    goToPreviousMonth: () -> Unit,
    goToNextMonth: () -> Unit,
) {


    if (showWeekView == false) {
        CalendarHeader(
            headerMonth!!,
            { goToPreviousMonth() },
            { goToNextMonth() })
        CalendarGrid(navController, currentMonth!!, monthEvents)
    }
}

@ExperimentalFoundationApi
@Composable
fun CalendarGrid(
    navController: NavController,
    currentMonth: CurrentMonth,
    monthEvents: List<Day>?,
) {

    val items: List<Any> = currentMonth.header + currentMonth.offset + currentMonth.days

    LazyVerticalGrid(
        cells = GridCells.Fixed(7)
    ) {

        items(items.size) {

            if (items[it] is Calendar) {

                var bgColor = Color.White
                var txtColor = Color.Black
                var onClick = {}

                if (monthEvents!!.find { event -> event.date == getDateString(items[it] as Calendar) } != null) {

                    val index =
                        monthEvents.indexOfFirst { event -> event.date!! == getDateString(items[it] as Calendar) }

                    bgColor = colorResource(R.color.pale_blue)

                    if (monthEvents[index].events!!.size == 1) {

                        val eventBundle = EventBundle(
                            date = "${weekDaysCalendarClass[(items[it] as Calendar)[Calendar.DAY_OF_WEEK]]}, ${
                                getDateString(
                                    (items[it] as Calendar),
                                    "."
                                )
                            }",
                            time = "${monthEvents[index].events!![0].timeStart} - ${monthEvents[index].events!![0].timeEnd}",
                            name = monthEvents[index].events!![0].name,
                            invite = monthEvents[index].events!![0].invite,
                            users = monthEvents[index].events!![0].users

                        )
                        val bundle = Bundle()
                        bundle.putParcelable("event", eventBundle)

                        onClick =
                            {
                                navController.navigate(
                                    R.id.action_calendarFragment_to_eventFragment,
                                    bundle
                                )
                            }
                    } else if (monthEvents[index].events!!.size > 1) {

                        val dayBundle = DayBundle(
                            date = "${weekDaysCalendarClass[(items[it] as Calendar)[Calendar.DAY_OF_WEEK]]}, ${
                                getDateString(
                                    (items[it] as Calendar),
                                    "."
                                )
                            }",
                            events = monthEvents[index].events!!
                        )
                        val bundle = Bundle()
                        bundle.putParcelable("day", dayBundle)


                        onClick =
                            {
                                navController.navigate(
                                    R.id.action_calendarFragment_to_dayFragment,
                                    bundle
                                )
                            }
                    }

                }

                if (isDateSame((items[it] as Calendar), Calendar.getInstance())) {
                    bgColor = MaterialTheme.colors.secondary
                    txtColor = Color.White
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(bgColor)
                        .clickable(onClick = onClick)
                ) {
                    Text(
                        text = (items[it] as Calendar)[Calendar.DAY_OF_MONTH].toString(),
                        style = TextStyle(fontSize = 18.sp, color = txtColor),
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }

            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = items[it].toString(),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}