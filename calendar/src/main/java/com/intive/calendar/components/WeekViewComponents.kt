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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.isDateSame
import com.intive.calendar.utils.weekDays
import com.intive.calendar.utils.weekDaysCalendarClass
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import java.util.*

@Composable
fun WeekView(
    currentWeek: Array<CalendarHomeViewModel.DayWeek>?,
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {
    val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()
    val header: String? by calendarViewModel.weekHeader.observeAsState()

    if (showWeekView == true) {
        CalendarHeader(
            header!!,
            { calendarViewModel.goToPreviousWeek() },
            { calendarViewModel.goToNextWeek() })
        DaysList(currentWeek, navController)
    }
}

@Composable
fun DaysList(
    currentWeek: Array<CalendarHomeViewModel.DayWeek>?,
    navController: NavController
) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(7) {
            currentWeek?.get(it)
                ?.let { day -> DaysListItem(it, navController, day) }
        }
    }
}


@Composable
fun DaysListItem(
    index: Int,
    navController: NavController,
    day: CalendarHomeViewModel.DayWeek
) {

    var bkgColor: Color = Color.White
    var txtColor: Color = Color.Black


    if (isDateSame(day.date, Calendar.getInstance())) {
        bkgColor = MaterialTheme.colors.secondary
        txtColor = Color.White
    } else if (day.date.before(Calendar.getInstance())) {
        txtColor = Color.Gray
    }

    val headerColor: Color = txtColor

    when {
        day.events.isEmpty() -> {
            if (!isDateSame(day.date, Calendar.getInstance())) {
                txtColor = Color.Gray
            }
            WeekDayWithEvents(
                bkgColor,
                headerColor,
                txtColor,
                stringResource(R.string.no_events),
                {},
                index,
                day.date
            )
        }
        day.events.size == 1 -> {

            val header =
                "${weekDaysCalendarClass[day.date[Calendar.DAY_OF_WEEK]]}, ${day.date[Calendar.DAY_OF_MONTH]}.${day.date[Calendar.MONTH] + 1}.${day.date[Calendar.YEAR]}"
            val bundle = bundleOf(
                "date" to header,
                "time" to day.events[0].time,
                "name" to day.events[0].name
            )

            WeekDayWithEvents(
                bkgColor, headerColor,
                txtColor,
                "${day.events[0].name}, ${day.events[0].time}",
                {
                    navController.navigate(
                        R.id.action_calendarFragment_to_eventFragment,
                        bundle
                    )
                },
                index,
                day.date
            )
        }
        else -> {

            val eventsShow = remember { mutableStateOf(false) }

            WeekDayWithEvents(
                bkgColor,
                headerColor,
                txtColor,
                "${stringResource(R.string.events_number)}: ${day.events.size}",
                {
                    eventsShow.value = eventsShow.value != true
                },
                index,
                day.date,
            )

            if (eventsShow.value) {
                EventsList(
                    bkgColor,
                    headerColor,
                    day.events, day.date, navController
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
    events: List<CalendarHomeViewModel.Event>,
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
    event: CalendarHomeViewModel.Event,
    date: Calendar,
    navController: NavController
) {

    val header =
        "${weekDaysCalendarClass[date[Calendar.DAY_OF_WEEK]]}, ${date[Calendar.DAY_OF_MONTH]}.${date[Calendar.MONTH] + 1}.${date[Calendar.YEAR]}"

    val bundle = bundleOf(
        "date" to header,
        "time" to event.time,
        "name" to event.name
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
            "${event.name}, ",
            style = TextStyle(
                color = headerColor,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        )
        Text(
            event.time,
            style = TextStyle(
                color = headerColor,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        )
    }
}

