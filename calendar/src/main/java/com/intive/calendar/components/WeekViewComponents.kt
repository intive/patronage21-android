package com.intive.calendar.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.screens.DayEvents
import com.intive.calendar.screens.EventsList
import com.intive.calendar.utils.isDateSame
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
            DayEvents(
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

            DayEvents(
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

            DayEvents(
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