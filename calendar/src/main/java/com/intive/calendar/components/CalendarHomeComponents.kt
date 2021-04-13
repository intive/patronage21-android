package com.intive.calendar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.calendar.utils.isDateSame
import com.intive.calendar.utils.weekDays

import com.intive.calendar.utils.week_days
import java.util.*


@ExperimentalFoundationApi
@Composable
fun CalendarHomeLayout(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {

    val currentWeek: Array<Calendar>? by calendarViewModel.currentWeek.observeAsState()
    val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()

    val calendarViewStr: String =
        if (showWeekView == true) stringResource(R.string.week) else stringResource(R.string.month)

    val period =
        "${currentWeek?.get(0)?.get(Calendar.DAY_OF_MONTH)}.${
            currentWeek?.get(0)?.get(Calendar.MONTH)?.plus(1)
        }.${currentWeek?.get(0)?.get(Calendar.YEAR)}" +
                "-${currentWeek?.get(6)?.get(Calendar.DAY_OF_MONTH)}.${
                    currentWeek?.get(6)?.get(Calendar.MONTH)?.plus(1)
                }.${currentWeek?.get(6)?.get(Calendar.YEAR)}"

    Scaffold(
        backgroundColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(R.id.action_calendarFragment_to_addEventFragment) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add new event button")
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

                WeekView(period, currentWeek, navController)
                MonthView(navController)
            }
        }
    }
}


@Composable
fun WeekView(
    period: String,
    currentWeek: Array<Calendar>?,
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {
    val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()
    if (showWeekView == true) {

        CalendarHeader(
            period,
            { calendarViewModel.goToPreviousWeek() },
            { calendarViewModel.goToNextWeek() })
        DaysList(currentWeek, navController)
    }
}

@Composable
fun DaysList(
    currentWeek: Array<Calendar>?,
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {
    val scrollState = rememberLazyListState()
    val days = calendarViewModel.days
    LazyColumn(state = scrollState) {
        items(7) {
            currentWeek?.get(it)?.let { it1 -> DaysListItem(it1, it, navController, days[it]) }
        }
    }
}

@Composable
fun DaysListItem(
    date: Calendar,
    index: Int,
    navController: NavController,
    day: CalendarHomeViewModel.Day
) {

    var bkgColor: Color = Color.White
    var txtColor: Color = Color.Black


    if (isDateSame(date, Calendar.getInstance())) {
        bkgColor = colors.secondary
        txtColor = Color.White
    } else if (date.before(Calendar.getInstance())) {
        txtColor = Color.Gray
    }

    var headerColor: Color = txtColor

    when {
        day.events.isEmpty() -> {
            if (!isDateSame(date, Calendar.getInstance())) {
                txtColor = Color.Gray
            }
            DayEvents(
                bkgColor,
                headerColor,
                txtColor,
                stringResource(R.string.no_events),
                {},
                index,
                date
            )
        }
        day.events.size == 1 -> {

            val header =
                "${week_days[date[Calendar.DAY_OF_WEEK]]}, ${date[Calendar.DAY_OF_MONTH]}.${date[Calendar.MONTH] + 1}.${date[Calendar.YEAR]}"
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
                date
            )
        }
        else -> {
            val header =
                "${week_days[date[Calendar.DAY_OF_WEEK]]}, ${date[Calendar.DAY_OF_MONTH]}.${date[Calendar.MONTH] + 1}.${date[Calendar.YEAR]}"

            val bundle = bundleOf("header" to header, "events" to Gson().toJson(day.events))
            DayEvents(
                bkgColor,
                headerColor,
                txtColor,
                "Liczba wydarzeń: ${day.events.size}",
                { navController.navigate(R.id.action_calendarFragment_to_dayFragment, bundle) },
                index,
                date
            )
        }
    }
}


@Composable
fun DayEvents(
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


            Divider(color = Color.LightGray)
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun MonthView(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {
    val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()
    val month: Int? by calendarViewModel.month.observeAsState()
    val year: Int? by calendarViewModel.year.observeAsState()
    if (showWeekView == false) {
        val monthStr = "${month?.plus(1)}.${year}"
        CalendarHeader(
            monthStr,
            { calendarViewModel.goToPreviousMonth() },
            { calendarViewModel.goToNextMonth() })
        CalendarGrid(navController)
    }
}

@ExperimentalFoundationApi
@Composable
fun CalendarGrid(
    navController: NavController,
    calendarViewModel: CalendarHomeViewModel = viewModel()
) {
    val currentMonth: List<Any>? by calendarViewModel.currentMonth.observeAsState()
    val items = currentMonth?.toList()

    LazyVerticalGrid(
        cells = GridCells.Fixed(7)
    ) {
        if (items != null) {
            items(items.size) {
                if (items[it] is CalendarHomeViewModel.Day) {
                    var bgColor = Color.White
                    var onClick = {}
                    if ((items[it] as CalendarHomeViewModel.Day).events.isNotEmpty()) {

                        bgColor = colorResource(R.color.pale_blue)

                        if ((items[it] as CalendarHomeViewModel.Day).events.size == 1) {
                            val bundle = bundleOf(
                                "date" to "${(items[it] as CalendarHomeViewModel.Day).index}.04.2021",
                                "time" to (items[it] as CalendarHomeViewModel.Day).events[0].time,
                                "name" to (items[it] as CalendarHomeViewModel.Day).events[0].name
                            )
                            onClick =
                                {
                                    navController.navigate(
                                        R.id.action_calendarFragment_to_eventFragment,
                                        bundle
                                    )
                                }
                        } else if ((items[it] as CalendarHomeViewModel.Day).events.size > 1) {
                            val bundle = bundleOf(
                                "header" to "${(items[it] as CalendarHomeViewModel.Day).index}.04.2021",
                                "events" to Gson().toJson((items[it] as CalendarHomeViewModel.Day).events)
                            )
                            onClick =
                                {
                                    navController.navigate(
                                        R.id.action_calendarFragment_to_dayFragment,
                                        bundle
                                    )
                                }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(bgColor)
                            .clickable(onClick = onClick)
                    ) {
                        if ((items[it] as CalendarHomeViewModel.Day).index == 12) {
                            Text(
                                text = (items[it] as CalendarHomeViewModel.Day).index.toString(),
                                style = TextStyle(
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        } else {
                            Text(
                                text = (items[it] as CalendarHomeViewModel.Day).index.toString(),
                                style = TextStyle(fontSize = 18.sp),
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }

                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = items[it].toString(),
                            style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

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
                } else {
                    calendarViewModel.showMonthView()
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
fun CalendarViewOption(text: String, bColor: Color, tColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bColor)
            .clickable(onClick = onClick)
    ) {
        Text(
            text,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                color = tColor,
                fontSize = 20.sp,
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
                contentDescription = "Right button",
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
            .background(MaterialTheme.colors.primary)
    ) {

        IconButton(onClick = onClickPrev) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "Left button",
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
                contentDescription = "Right button",
                tint = Color.White
            )
        }
    }
}