package com.intive.calendar.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.ui.components.TitleText


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

    val calendarViewStr: String =
        if (showWeekView == true) stringResource(R.string.week) else stringResource(R.string.month)

    Box(contentAlignment = Alignment.BottomEnd) {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            TitleText(stringResource(R.string.calendar), Modifier.padding(bottom = 24.dp))
            Paragraph(
                stringResource(R.string.lorem_ipsum),
                Modifier.padding(bottom = 24.dp)
            )

            ViewOptionsComponent({ calendarViewModel.showDialog() }, calendarViewStr)

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

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(R.id.action_calendarFragment_to_addEventFragment) },
                backgroundColor = colors.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_event_btn_desc)
                )
            }
        }
    }
}


@Composable
fun ChoosePeriodDialog(calendarViewModel: CalendarHomeViewModel) {
    val showPeriodDialog by calendarViewModel.showPeriodDialog.observeAsState()
    val weekClicked by calendarViewModel.weekClicked.observeAsState()
    val bColorWeekBtn by calendarViewModel.bColorWeekBtn.observeAsState()
    val bColorMonthBtn by calendarViewModel.bColorMonthBtn.observeAsState()
    val txtColorWeekBtn by calendarViewModel.txtColorWeekBtn.observeAsState()
    val txtColorMonthBtn by calendarViewModel.txtColorMonthBtn.observeAsState()

    if (showPeriodDialog == true) {
        Dialog(onDismissRequest = { calendarViewModel.hideDialog() }) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                ClearButton { calendarViewModel.hideDialog() }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(24.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {

                        TitleText(
                            text = stringResource(R.string.choose_period),
                            modifier = Modifier.padding(bottom = 24.dp),
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )

                        CalendarViewOption(
                            text = stringResource(R.string.week),
                            bgColor = Color(bColorWeekBtn!!),
                            txtColor = Color(txtColorWeekBtn!!)
                        ) {
                            calendarViewModel.weekClicked()
                        }

                        CalendarViewOption(
                            text = stringResource(R.string.month),
                            bgColor = Color(bColorMonthBtn!!),
                            txtColor = Color(txtColorMonthBtn!!)
                        ) {
                            calendarViewModel.monthClicked()
                        }
                    }

                    Column {
                        OKButton(text = stringResource(R.string.accept)) {
                            if (weekClicked == true) {
                                calendarViewModel.showWeekView()
                                calendarViewModel.refreshCalendar()
                            } else {
                                calendarViewModel.showMonthView()
                                calendarViewModel.refreshCalendar()
                            }
                            calendarViewModel.hideDialog()
                        }

                        CancelButton(text = stringResource(R.string.go_back)) {
                            calendarViewModel.hideDialog()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CalendarViewOption(text: String, bgColor: Color, txtColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                color = txtColor,
                fontSize = 18.sp,
            )
        )
    }
}


@Composable
fun ViewOptionsComponent(showDialog: () -> Unit, calendarViewString: String) {

    Card(
        border = BorderStroke(0.5.dp,Color.Black), modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = calendarViewString,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.align(Alignment.CenterVertically).padding(start = 12.dp)
            )

            IconButton(onClick = showDialog) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.spinner_component_btn_desc),
                    tint = Color.Black
                )
            }
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