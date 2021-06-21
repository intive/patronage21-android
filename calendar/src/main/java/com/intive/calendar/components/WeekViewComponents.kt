package com.intive.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.intive.calendar.fragments.CalendarHomeFragmentDirections
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.*
import java.util.*
import com.intive.repository.domain.model.Event
import com.intive.shared.getHour
import com.intive.shared.EventParcelable
import com.intive.shared.getDate
import com.intive.shared.getDateString
import com.intive.shared.getFullDateString


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

    (0..6).forEach {
        if (weekEventsList.find { event -> event.date == getDate(currentWeek[it]) } == null) {
            DaysListItem(
                index = it,
                navController = navController,
                day = currentWeek[it],
                events = emptyList()
            )
        } else {
            val index =
                weekEventsList.indexOfFirst { event -> event.date!! == getDate(currentWeek[it]) }
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
                headerColor = headerColor,
                txtColor = txtColor,
                text = stringResource(R.string.no_events),
                index = index,
                date = day,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bkgColor)
            )
        }
        events.size == 1 -> {

            val header = getFullDateString(day)

            val eventParcelable = EventParcelable(
                id = events[0]._id,
                date = header,
                time = "${events[0].startDate.getHour()} - ${events[0].endDate.getHour()}",
                name = events[0].title,
                active = isDayActive
            )

            val directions = CalendarHomeFragmentDirections.actionCalendarFragmentToEventFragment(
                eventInfoParcelable = eventParcelable
            )

            WeekDayWithEvents(
                headerColor = headerColor,
                txtColor = txtColor,
                text = "${events[0].title}, ${events[0].startDate.getHour()} - ${events[0].endDate.getHour()}",
                index = index,
                date = day,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bkgColor)
                    .clickable(onClick = {
                        navController.navigate(
                            directions
                        )
                    })
            )
        }
        else -> {

            val eventsShow = remember { mutableStateOf(false) }

            WeekDayWithEvents(
                headerColor = headerColor,
                txtColor = txtColor,
                text = "${stringResource(R.string.events_number)}: ${events.size}",
                index = index,
                date = day,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(bkgColor)
                    .clickable(onClick = { eventsShow.value = eventsShow.value != true })
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
    headerColor: Color,
    txtColor: Color,
    text: String,
    index: Int,
    date: Calendar,
    modifier: Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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

    val header = getFullDateString(date)

    val eventParcelable = EventParcelable(
        id = event._id,
        date = header,
        time = "${event.startDate.getHour()} - ${event.endDate.getHour()}",
        name = event.title,
        active = isDayActive
    )

    val directions =
        CalendarHomeFragmentDirections.actionCalendarFragmentToEventFragment(eventInfoParcelable = eventParcelable)

    Row(
        modifier = Modifier
            .background(bkgColor)
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(
                    directions
                )
            })
            .padding(start = 10.dp, top = 12.dp, bottom = 12.dp)
    ) {

        Text(
            text = "${event.title}, ${event.startDate.getHour()} - ${event.endDate.getHour()}",
            style = TextStyle(
                color = headerColor,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        )
    }
}

