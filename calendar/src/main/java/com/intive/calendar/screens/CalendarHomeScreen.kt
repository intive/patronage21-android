package com.intive.calendar.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.components.*


@ExperimentalFoundationApi
@Composable
fun CalendarHomeLayout(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel
) {

    val currentWeek by calendarViewModel.currentWeek.observeAsState()
    val weekEventsList by calendarViewModel.weekEvents.observeAsState()
    val showWeekView by calendarViewModel.showWeekView.observeAsState()
    val weekHeader by calendarViewModel.weekHeader.observeAsState()
    val monthHeader by calendarViewModel.monthHeader.observeAsState()
    val currentMonth by calendarViewModel.currentMonth.observeAsState()
    val monthEvents by calendarViewModel.monthEvents.observeAsState()
    val calendarViewsList = stringArrayResource(R.array.calendar_views_list).asList()


    LayoutContainer {
        FABLayout(
            onClick = { navController.navigate(R.id.action_calendarFragment_to_addEventFragment) },
            contentDescription = stringResource(R.string.add_event_btn_desc)
        ) {
            IntroSection(
                title = stringResource(R.string.calendar),
                text = stringResource(R.string.lorem_ipsum)
            )

            Spinner(
                items = calendarViewsList,
                onTitleSelected = calendarViewModel::onCalendarViewChange
            )
            Spacer(modifier = Modifier.height(16.dp))

            WeekView(
                currentWeek = currentWeek,
                weekEventsList = weekEventsList,
                navController = navController,
                showWeekView = showWeekView,
                header = weekHeader,
                goToPreviousWeek = { calendarViewModel.goToPreviousWeek() }
            ) { calendarViewModel.goToNextWeek() }
            MonthView(
                navController = navController,
                showWeekView = showWeekView,
                headerMonth = monthHeader,
                currentMonth = currentMonth,
                monthEvents = monthEvents,
                goToPreviousMonth = { calendarViewModel.goToPreviousMonth() }
            ) { calendarViewModel.goToNextMonth() }
        }
    }


}


@Composable
fun CalendarHeader(period: String, onClickPrev: () -> Unit, onClickNext: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.primary)
    ) {

        IconButton(onClick = onClickPrev) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.calendar_header_left_btn_desc),
                tint = Color.White
            )
        }

        Text(
            text = period,
            style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        IconButton(onClick = onClickNext) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.calendar_header_right_btn_desc),
                tint = Color.White
            )
        }
    }
}