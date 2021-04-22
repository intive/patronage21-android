package com.intive.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.getDateString
import com.intive.calendar.utils.isDateSame
import com.intive.calendar.utils.weekDays
import com.intive.calendar.utils.weekDaysCalendarClass
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import java.util.*
import com.intive.calendar.utils.Day
import com.intive.repository.domain.model.Event


@Composable
fun WeekView(
    currentWeek: Array<Calendar>,
    weekEventsList: List<Day>,
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel
) {
    val showWeekView by calendarViewModel.showWeekView.observeAsState()
    val header by calendarViewModel.weekHeader.observeAsState()

    if (showWeekView == true) {
        CalendarHeader(
            header!!,
            { calendarViewModel.goToPreviousWeek() },
            { calendarViewModel.goToNextWeek() })
        DaysList(currentWeek, weekEventsList, navController)
    }
}

@Composable
fun DaysList(
    currentWeek: Array<Calendar>,
    weekEventsList: List<Day>,
    navController: NavController
) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(7) {
            if (weekEventsList!!.find { it1 -> it1.date == getDateString(currentWeek[it]) } == null) {
                DaysListItem(it, navController, currentWeek[it], emptyList())
            } else {
                val index =
                    weekEventsList!!.indexOfFirst { it2 -> it2.date!! == getDateString(currentWeek[it]) }
                weekEventsList[index].events?.let { it1 ->
                    DaysListItem(
                        it, navController, currentWeek[it],
                        it1
                    )
                }
            }
        }
    }
}


@Composable
fun DaysListItem(
    index: Int,
    navController: NavController,
    day: Calendar,
    events: List<Event>
) {

    var bkgColor: Color = Color.White
    var txtColor: Color = Color.Black


    if (isDateSame(day, Calendar.getInstance())) {
        bkgColor = MaterialTheme.colors.secondary
        txtColor = Color.White
    } else if (day.before(Calendar.getInstance())) {
        txtColor = Color.Gray
    }

    val headerColor: Color = txtColor

    when {
        events.isEmpty() -> {
            if (!isDateSame(day, Calendar.getInstance())) {
                txtColor = Color.Gray
            }
            WeekDayWithEvents(
                bkgColor,
                headerColor,
                txtColor,
                stringResource(R.string.no_events),
                {},
                index,
                day
            )
        }
        events.size == 1 -> {

            val header =
                "${weekDaysCalendarClass[day[Calendar.DAY_OF_WEEK]]}, ${day[Calendar.DAY_OF_MONTH]}.${day[Calendar.MONTH] + 1}.${day[Calendar.YEAR]}"
            val bundle = bundleOf(
                "date" to header,
                "time" to "${events[0].timeStart} - ${events[0].timeEnd}",
                "name" to events[0].name,
                "users" to Gson().toJson(events[0].users)
            )

            WeekDayWithEvents(
                bkgColor, headerColor,
                txtColor,
                "${events[0].name}, ${events[0].timeStart} - ${events[0].timeEnd}",
                {
                    navController.navigate(
                        R.id.action_calendarFragment_to_eventFragment,
                        bundle
                    )
                },
                index,
                day
            )
        }
        else -> {

            val eventsShow = remember { mutableStateOf(false) }

            WeekDayWithEvents(
                bkgColor,
                headerColor,
                txtColor,
                "${stringResource(R.string.events_number)}: ${events.size}",
                {
                    eventsShow.value = eventsShow.value != true
                },
                index,
                day,
            )

            if (eventsShow.value) {
                EventsList(
                    bkgColor,
                    headerColor,
                    events, day, navController
                )
            }
        }
    }
    Divider(color = Color.LightGray)
}

@Composable
fun WeekDayWithEvents(
    bkgColor: Color,
    headerColor: Color,
    txtColor: Color,
    text: String,
    onClickDayItem: () -> Unit,
    index: Int,
    date: Calendar
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(bkgColor)
            .clickable(onClick = onClickDayItem)
    ) {
        Spacer(Modifier.width(10.dp))
        Column {

            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(
                    "${weekDays[index]}, ${date[Calendar.DAY_OF_MONTH]}.${date[Calendar.MONTH] + 1}.${date[Calendar.YEAR]}",
                    style = TextStyle(
                        color = headerColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(
                    text,
                    style = TextStyle(color = txtColor),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun EventsList(
    bkgColor: Color,
    headerColor: Color,
    events: List<Event>,
    date: Calendar,
    navController: NavController
) {
    Column {
        for (event in events) {
            EventsItem(
                bkgColor,
                headerColor,
                event, date, navController
            )
        }
    }
}

@Composable
fun EventsItem(
    bkgColor: Color,
    headerColor: Color,
    event: Event,
    date: Calendar,
    navController: NavController
) {

    val header =
        "${weekDaysCalendarClass[date[Calendar.DAY_OF_WEEK]]}, ${date[Calendar.DAY_OF_MONTH]}.${date[Calendar.MONTH] + 1}.${date[Calendar.YEAR]}"

    val bundle = bundleOf(
        "date" to header,
        "time" to "${event.timeStart} - ${event.timeEnd}",
        "name" to event.name,
        "users" to Gson().toJson(event.users)
    )

    Row(
        modifier = Modifier
            .background(bkgColor)
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(
                    R.id.action_calendarFragment_to_eventFragment,
                    bundle
                )
            })
            .padding(start = 10.dp, top = 12.dp, bottom = 12.dp)
    ) {

        Text(
            "${event.name}, ${event.timeStart} - ${event.timeEnd}",
            style = TextStyle(
                color = headerColor,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        )
    }
}

