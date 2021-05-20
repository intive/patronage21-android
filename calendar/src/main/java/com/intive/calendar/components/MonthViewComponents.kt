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
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.fragments.CalendarHomeFragmentDirections
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.*
import com.intive.shared.EventBundle
import com.intive.shared.getDateString
import com.intive.shared.getFullDateString
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
            period = headerMonth!!,
            onClickPrev = { goToPreviousMonth() },
            onClickNext = { goToNextMonth() })
        CalendarGrid(
            navController = navController,
            currentMonth = currentMonth!!,
            monthEvents = monthEvents
        )
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
                val onClick: () -> Unit

                if (isDateSame((items[it] as Calendar), Calendar.getInstance())) {
                    bgColor = MaterialTheme.colors.secondary
                    txtColor = Color.White
                } else if ((items[it] as Calendar).before(Calendar.getInstance())) {
                    txtColor = Color.Gray
                }

                var dayColumnModifier = Modifier.background(bgColor)

                if (monthEvents!!.find { event -> event.date == getDateString(items[it] as Calendar) } != null) {

                    val index =
                        monthEvents.indexOfFirst { event -> event.date!! == getDateString(items[it] as Calendar) }

                    if(!(isDateSame((items[it] as Calendar), Calendar.getInstance()))){
                        bgColor = colorResource(R.color.pale_blue)
                    }


                    when {
                        monthEvents[index].events!!.size == 1 -> {
                            val isDayActive = !((items[it] as Calendar).before(Calendar.getInstance()))

                            val eventBundle = EventBundle(
                                id = monthEvents[index].events!![0].id,
                                date = getFullDateString(items[it] as Calendar),
                                time = "${monthEvents[index].events!![0].timeStart} - ${monthEvents[index].events!![0].timeEnd}",
                                name = monthEvents[index].events!![0].name,
                                inviteResponse = monthEvents[index].events!![0].inviteResponse,
                                users = monthEvents[index].events!![0].users,
                                active = isDayActive

                            )

                            val eventSerialized = Gson().toJson(eventBundle)
                            val directions = CalendarHomeFragmentDirections.actionCalendarFragmentToEventFragment(eventSerialized)

                            onClick =
                                {
                                    navController.navigate(
                                        directions
                                    )
                                }

                            dayColumnModifier = Modifier
                                .background(bgColor)
                                .clickable(onClick = onClick)
                        }
                        monthEvents[index].events!!.size > 1 -> {
                            val isDayActive = !((items[it] as Calendar).before(Calendar.getInstance()))

                            val dayBundle = DayBundle(
                                date = getFullDateString(items[it] as Calendar),
                                events = monthEvents[index].events!!,
                                active = isDayActive
                            )
                            val bundle = Bundle()
                            bundle.putParcelable(dayBundleKey, dayBundle)


                            onClick =
                                {
                                    navController.navigate(
                                        R.id.action_calendarFragment_to_dayFragment,
                                        bundle
                                    )
                                }

                            dayColumnModifier = Modifier
                                .background(bgColor)
                                .clickable(onClick = onClick)
                        }
                        else -> {
                            dayColumnModifier = Modifier
                                .background(bgColor)
                        }
                    }

                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = dayColumnModifier
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