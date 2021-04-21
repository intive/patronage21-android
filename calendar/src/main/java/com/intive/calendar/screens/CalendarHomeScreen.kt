package com.intive.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

            SpinnerComponent({ calendarViewModel.showDialog() }, calendarViewStr)

            WeekView(currentWeek!!, weekEventsList!!, navController, calendarViewModel)
            MonthView(navController, calendarViewModel)
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
    if (showPeriodDialog == true) {
        Dialog(onDismissRequest = { calendarViewModel.hideDialog() }) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                ClearButton { calendarViewModel.hideDialog() }
                PeriodDialogLayout(calendarViewModel)
            }
        }
    }
}

@Composable
fun PeriodDialogLayout(calendarViewModel: CalendarHomeViewModel) {

    val weekClicked by calendarViewModel.weekClicked.observeAsState()
    val bColorWeekBtn by calendarViewModel.bColorWeekBtn.observeAsState()
    val bColorMonthBtn by calendarViewModel.bColorMonthBtn.observeAsState()
    val txtColorWeekBtn by calendarViewModel.txtColorWeekBtn.observeAsState()
    val txtColorMonthBtn by calendarViewModel.txtColorMonthBtn.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            TitleText(
                stringResource(R.string.choose_period),
                Modifier.padding(bottom = 24.dp),
                MaterialTheme.typography.h6,
                Color.Black
            )

            CalendarViewOption(
                stringResource(R.string.week), Color(bColorWeekBtn!!), Color(txtColorWeekBtn!!)
            ) {
                calendarViewModel.weekClicked()
            }

            CalendarViewOption(
                stringResource(R.string.month),
                Color(bColorMonthBtn!!),
                Color(txtColorMonthBtn!!)
            ) {
                calendarViewModel.monthClicked()
            }
        }

        Column {
            OKButton(stringResource(R.string.accept)) {
                if (weekClicked == true) {
                    calendarViewModel.showWeekView()
                    calendarViewModel.setCurrentMonth()
                } else {
                    calendarViewModel.showMonthView()
                    calendarViewModel.setCurrentWeek()
                }
                calendarViewModel.hideDialog()
            }

            CancelButton(stringResource(R.string.go_back)) {
                calendarViewModel.hideDialog()
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
            text,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                color = txtColor,
                fontSize = 18.sp,
            )
        )
    }
}


@Composable
fun SpinnerComponent(showDialog: () -> Unit, calendarViewStr: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        Text(
            calendarViewStr,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.align(Alignment.CenterVertically)
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
            period,
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