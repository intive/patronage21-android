@file:JvmName("CalendarMainFragmentKt")

package com.example.patron_a_tive.calendar_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patron_a_tive.R
import java.util.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.patron_a_tive.calendar_module.components.*
import com.example.patron_a_tive.calendar_module.viewmodels.CalendarHomeViewModel


class CalendarHomeFragment : Fragment() {

    private lateinit var navController: NavController

    private val weekDays =
        arrayOf("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela")

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.theme?.applyStyle(R.style.ThemeDialogFullScreen, true)
        return ComposeView(requireContext()).apply {
            setContent {
                WeekFragmentLayout()
                ChoosePeriodDialog()
            }
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

    @ExperimentalFoundationApi
    @Composable
    fun WeekFragmentLayout(calendarViewModel: CalendarHomeViewModel = viewModel()) {

        navController = findNavController()

        val currentWeek: Array<Calendar>? by calendarViewModel.currentWeek.observeAsState()
        val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()

        var calendarViewStr: String = if (showWeekView == true) "Tydzień" else "Miesiąc"

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
                FloatingActionButton(onClick = {
                    navController?.navigate(R.id.action_calendarFragment_to_addEventFragment)
                }, backgroundColor = MaterialTheme.colors.primary) {
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

                    WeekView(period, currentWeek)
                    MonthView()

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
                .background(Color(0xffcc4c80.toInt()))
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
                    color = Color(0xffffffff.toInt()),
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

    @Composable
    fun WeekView(
        period: String,
        currentWeek: Array<Calendar>?,
        calendarViewModel: CalendarHomeViewModel = viewModel()
    ) {
        val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()
        if (showWeekView == true) {

            CalendarHeader(
                period,
                { calendarViewModel.goToPreviousWeek() },
                { calendarViewModel.goToNextWeek() })
            DaysList(currentWeek)
        }
    }

    @Composable
    fun DaysList(currentWeek: Array<Calendar>?) {
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(7) {
                currentWeek?.get(it)?.let { it1 -> DaysListItem(it1, it) }
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun MonthView(calendarViewModel: CalendarHomeViewModel = viewModel()) {
        val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()
        val month: Int? by calendarViewModel.month.observeAsState()
        val year: Int? by calendarViewModel.year.observeAsState()
        if (showWeekView == false) {
            val monthStr: String = "${month?.plus(1)}.${year}"
            CalendarHeader(
                monthStr,
                { calendarViewModel.goToPreviousMonth() },
                { calendarViewModel.goToNextMonth() })
            CalendarGrid()
        }


    }

    @ExperimentalFoundationApi
    @Composable
    fun CalendarGrid(calendarViewModel: CalendarHomeViewModel = viewModel()) {
        val currentMonth: List<String>? by calendarViewModel.currentMonth.observeAsState()

        LazyVerticalGrid(
            cells = GridCells.Fixed(7)
        ) {
            if (currentMonth != null) {
                items(currentMonth!!.size) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentMonth!![it],
                            style = TextStyle(fontSize = 18.sp),
                            modifier = Modifier.padding(4.dp)
                        )
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
                    PeriodFragmentLayout()
                }
            }
        }
    }


    @Composable
    fun DaysListItem(date: Calendar, index: Int) {

        var bkgColor: Color = Color.White
        var txtColor: Color = Color.Black

        if (isDateSame(date, Calendar.getInstance())) {
            bkgColor = MaterialTheme.colors.secondary
            txtColor = Color.White
        } else if (date.before(Calendar.getInstance())) {
            txtColor = Color.Gray
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(bkgColor)
                .clickable(onClick = {
                    navController?.navigate(R.id.action_calendarFragment_to_dayFragment)
                })
        ) {
            Spacer(Modifier.width(10.dp))
            Column {

                Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                    Text(
                        "${weekDays[index]}, ${date[Calendar.DAY_OF_MONTH]}.${date[Calendar.MONTH] + 1}.${date[Calendar.YEAR]}",
                        style = TextStyle(
                            color = txtColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }
                Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                    Text(
                        stringResource(R.string.no_events),
                        style = TextStyle(color = txtColor),
                        fontSize = 18.sp
                    )
                }

                Divider(color = Color.LightGray)
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
    fun PeriodFragmentLayout(calendarViewModel: CalendarHomeViewModel = viewModel()) {

        val bColorWeekBtn: Long? by calendarViewModel.bColorWeekBtn.observeAsState()
        val bColorMonthBtn: Long? by calendarViewModel.bColorMonthBtn.observeAsState()
        val txtColorWeekBtn: Long? by calendarViewModel.txtColorWeekBtn.observeAsState()
        val txtColorMonthBtn: Long? by calendarViewModel.txtColorMonthBtn.observeAsState()


        var weekClicked = remember {
            mutableStateOf(true)
        }

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
                    weekClicked.value = true
                }

                CalendarViewOption(
                    stringResource(R.string.month),
                    Color(bColorMonthBtn!!),
                    Color(txtColorMonthBtn!!)
                ) {
                    calendarViewModel.monthClicked()
                    weekClicked.value = false
                }


            }

            Column {
                OKButton(stringResource(R.string.accept)) {
                    if (weekClicked.value) {
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

    private fun findNavController(): NavController {
        val navHostFragment =
            (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}


fun isDateSame(c1: Calendar, c2: Calendar): Boolean {
    return c1[Calendar.YEAR] === c2[Calendar.YEAR] && c1[Calendar.MONTH] === c2[Calendar.MONTH] && c1[Calendar.DAY_OF_MONTH] === c2[Calendar.DAY_OF_MONTH]
}