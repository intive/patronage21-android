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
import com.example.patron_a_tive.calendar_module.viewmodels.CalendarHomeViewModel


class CalendarHomeFragment : Fragment() {

    private lateinit var calendar: Calendar
    private lateinit var navController: NavController


    private val weekDays =
        arrayOf("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = Calendar.getInstance()
    }

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
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "FAB")
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Kalendarz",
                        style = TextStyle(
                            color = Color(0xff52bcff.toInt()),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        stringResource(R.string.lorem_ipsum),
                        style = TextStyle(
                            color = Color(0xff000000.toInt()),
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xffffffff.toInt()))
                            .padding(12.dp)
                    ) {

                        Text(
                            calendarViewStr,
                            style = TextStyle(
                                color = Color(0xff000000.toInt()),
                                fontSize = 18.sp
                            ),
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

                    WeekView(
                        period,
                        currentWeek,
                        onClickPrev = { calendarViewModel.goToPreviousWeek() },
                        onClickNext = { calendarViewModel.goToNextWeek() })
                    MonthView()

                }

            }
        }

    }

    @Composable
    fun WeekView(
        period: String,
        currentWeek: Array<Calendar>?,
        onClickPrev: () -> Unit,
        onClickNext: () -> Unit,
        calendarViewModel: CalendarHomeViewModel = viewModel()
    ) {
        val showWeekView: Boolean? by calendarViewModel.showWeekView.observeAsState()
        if (showWeekView == true) {
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffcc4c80.toInt()))
            ) {

                IconButton(onClick = { calendarViewModel.goToPreviousMonth() }) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Left button",
                        tint = Color.White
                    )
                }

                Text(
                    monthStr,
                    style = TextStyle(
                        color = Color(0xffffffff.toInt()),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)

                )

                IconButton(onClick = { calendarViewModel.goToNextMonth() }) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Right button",
                        tint = Color.White
                    )
                }

            }

            CalendarGrid()
        }


    }

    @ExperimentalFoundationApi
    @Composable
    fun CalendarGrid(calendarViewModel: CalendarHomeViewModel = viewModel()) {
        val currentMonth: List<String>? by calendarViewModel.currentMonth.observeAsState()
        //val calendarItems = currentMonth

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
    fun ClearButton(calendarViewModel: CalendarHomeViewModel = viewModel()) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(12.dp)
        ) {
            IconButton(onClick = { calendarViewModel.hideDialog() }) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Exit dialog button",
                    tint = Color.Black
                )
            }
        }
    }

    @Composable
    fun DaysListItem(date: Calendar, index: Int) {

        var bkgColor: Color = Color.White
        var txtColor: Color = Color.Black

        if (isDateSame(date, Calendar.getInstance())) {
            bkgColor = Color(0xff52bcff.toInt())
            txtColor = Color.White
        } else if(date.before(calendar)){
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
                    Text("Brak wydarzeń", style = TextStyle(color = txtColor), fontSize = 18.sp)
                }

                Divider(color = Color.LightGray)
            }

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
                Text(
                    stringResource(R.string.choose_period),
                    style = TextStyle(
                        color = Color(0xff000000.toInt()),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(bColorWeekBtn!!))
                        .clickable(onClick = {
                            calendarViewModel.weekClicked()
                            weekClicked.value = true
                        })
                ) {
                    Text(
                        stringResource(R.string.week),
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color(txtColorWeekBtn!!),
                            fontSize = 20.sp,
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(bColorMonthBtn!!))
                        .clickable(onClick = {
                            calendarViewModel.monthClicked()
                            weekClicked.value = false
                        })
                ) {
                    Text(
                        stringResource(R.string.month),
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color(txtColorMonthBtn!!),
                            fontSize = 20.sp,
                        )
                    )
                }
            }
            Column {
                Button(
                    onClick = {
                        if (weekClicked.value) {
                            calendarViewModel.showWeekView()
                        } else {
                            calendarViewModel.showMonthView()
                        }
                        calendarViewModel.hideDialog()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffcc4c80.toInt())),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.accept),
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )
                }
                Button(
                    onClick = { calendarViewModel.hideDialog() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff52bcff.toInt())),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text(
                        stringResource(R.string.go_back),
                        style = TextStyle(fontSize = 20.sp, color = Color.White)
                    )
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


