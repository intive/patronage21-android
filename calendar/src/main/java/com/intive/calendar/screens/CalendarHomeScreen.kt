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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.calendar.viewmodels.CalendarHomeViewModel


@ExperimentalFoundationApi
@Composable
fun CalendarHomeLayout(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {

    val currentWeek: Array<CalendarHomeViewModel.DayWeek>? by calendarViewModel.currentWeek.observeAsState()
    val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()

    val calendarViewStr: String =
        if (showWeekView == true) stringResource(R.string.week) else stringResource(R.string.month)

    Scaffold(
        backgroundColor = Color.White,
        floatingActionButton = {
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                HeaderLarge(stringResource(R.string.calendar))

                Paragraph(
                    stringResource(R.string.lorem_ipsum),
                    Modifier.padding(bottom = 24.dp)
                )

                SpinnerComponent(calendarViewModel, calendarViewStr)

                WeekView(currentWeek, navController)
                MonthView(navController)
            }
        }
    }
}


@Composable
fun ChoosePeriodDialog(calendarViewModel: CalendarHomeViewModel = viewModel()) {
    val showPeriodDialog: Boolean? by calendarViewModel.showPeriodDialog.observeAsState()
    if (showPeriodDialog == true) {
        Dialog(onDismissRequest = { calendarViewModel.hideDialog() }) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                ClearButton()
                PeriodDialogLayout()
            }
        }
    }
}

@Composable
fun PeriodDialogLayout(calendarViewModel: CalendarHomeViewModel = viewModel()) {

    val weekClicked: Boolean? by calendarViewModel.weekClicked.observeAsState()
    val bColorWeekBtn: Long? by calendarViewModel.bColorWeekBtn.observeAsState()
    val bColorMonthBtn: Long? by calendarViewModel.bColorMonthBtn.observeAsState()
    val txtColorWeekBtn: Long? by calendarViewModel.txtColorWeekBtn.observeAsState()
    val txtColorMonthBtn: Long? by calendarViewModel.txtColorMonthBtn.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            HeaderMedium(
                stringResource(R.string.choose_period),
                Modifier.padding(bottom = 24.dp)
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
fun SpinnerComponent(calendarViewModel: CalendarHomeViewModel, calendarViewStr: String) {
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

        IconButton(onClick = { calendarViewModel.showDialog() }) {
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