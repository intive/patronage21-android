package com.intive.calendar.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.*
import java.util.*
import com.intive.repository.domain.model.Event


@Composable
fun WeekView(
    currentWeek: Array<Calendar>?,
    weekEventsList: List<Day>?,
    navController: NavController,
    showWeekView: Boolean?,
    header: String?,
    goToPreviousWeek: () -> Unit,
    goToNextWeek: () -> Unit,
) {

    if (showWeekView == true) {
        CalendarHeader(
            period = header!!,
            onClickPrev = { goToPreviousWeek() },
            onClickNext = { goToNextWeek() })
        DaysList(
            currentWeek = currentWeek!!,
            weekEventsList = weekEventsList!!,
            navController = navController
        )
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
            if (weekEventsList.find { event -> event.date == getDateString(currentWeek[it]) } == null) {
                DaysListItem(
                    index = it,
                    navController = navController,
                    day = currentWeek[it],
                    events = emptyList()
                )
            } else {
                val index =
                    weekEventsList.indexOfFirst { event -> event.date!! == getDateString(currentWeek[it]) }
                weekEventsList[index].events?.let { event ->
                    DaysListItem(
                        index = it,
                        navController = navController,
                        day = currentWeek[it],
                        events = event
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
    var isDayActive = true


    if (isDateSame(day, Calendar.getInstance())) {
        bkgColor = MaterialTheme.colors.secondary
        txtColor = Color.White
    } else if (day.before(Calendar.getInstance())) {
        txtColor = Color.Gray
        isDayActive = false
    }

    val headerColor: Color = txtColor

    when {
        events.isEmpty() -> {
            if (!isDateSame(day, Calendar.getInstance())) {
                txtColor = Color.Gray
            }
            WeekDayWithEvents(
                bkgColor = bkgColor,
                headerColor = headerColor,
                txtColor = txtColor,
                text = stringResource(R.string.no_events),
                onClickDayItem = {},
                index = index,
                date = day
            )
        }
        events.size == 1 -> {

            val header =
                "${weekDaysCalendarClass[day[Calendar.DAY_OF_WEEK]]}, ${getDateString(day, ".")}"

            val event = EventBundle(
                id = events[0].id,
                date = header,
                time = "${events[0].timeStart} - ${events[0].timeEnd}",
                name = events[0].name,
                inviteResponse = events[0].inviteResponse,
                users = events[0].users,
                active = isDayActive
            )
            val bundle = Bundle()
            bundle.putParcelable("event", event)

            WeekDayWithEvents(
                bkgColor = bkgColor, headerColor = headerColor,
                txtColor = txtColor,
                text = "${events[0].name}, ${events[0].timeStart} - ${events[0].timeEnd}",
                onClickDayItem = {
                    navController.navigate(
                        R.id.action_calendarFragment_to_eventFragment,
                        bundle
                    )
                },
                index = index,
                date = day
            )
        }
        else -> {

            val eventsShow = remember { mutableStateOf(false) }

            WeekDayWithEvents(
                bkgColor = bkgColor,
                headerColor = headerColor,
                txtColor = txtColor,
                text = "${stringResource(R.string.events_number)}: ${events.size}",
                onClickDayItem = {
                    eventsShow.value = eventsShow.value != true
                },
                index = index,
                date = day,
            )

            if (eventsShow.value) {
                EventsList(
                    bkgColor = bkgColor,
                    headerColor = headerColor,
                    events = events, date = day, navController = navController, isDayActive
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
                    "${weekDays[index]}, ${getDateString(date, ".")}",
                    style = TextStyle(
                        color = headerColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }

            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(
                    text = text,
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
    navController: NavController,
    isDayActive: Boolean
) {
    Column {
        for (event in events) {
            EventsItem(
                bkgColor = bkgColor,
                headerColor = headerColor,
                event = event,
                date = date,
                navController = navController,
                isDayActive = isDayActive
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
    navController: NavController,
    isDayActive: Boolean
) {

    val header =
        "${weekDaysCalendarClass[date[Calendar.DAY_OF_WEEK]]}, ${getDateString(date, ".")}"

    val eventBundle = EventBundle(
        id = event.id,
        date = header,
        time = "${event.timeStart} - ${event.timeEnd}",
        name = event.name,
        inviteResponse = event.inviteResponse,
        users = event.users,
        active = isDayActive
    )

    val bundle = Bundle()
    bundle.putParcelable("event", eventBundle)

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
            text = "${event.name}, ${event.timeStart} - ${event.timeEnd}",
            style = TextStyle(
                color = headerColor,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        )
    }
}

