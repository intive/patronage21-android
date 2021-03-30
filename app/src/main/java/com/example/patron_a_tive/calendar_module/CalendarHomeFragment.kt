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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.patron_a_tive.R
import java.util.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class CalendarHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var calendar: Calendar
    private lateinit var currentWeek: MutableState<Array<Calendar>>
    private lateinit var currentMonth: MutableState<List<String>>
    private lateinit var showPeriodDialog: MutableState<Boolean>
    private lateinit var showDayDialog: MutableState<Boolean>
    private lateinit var showWeekView: MutableState<Boolean>
    private lateinit var month: MutableState<Int>
    private lateinit var year: MutableState<Int>
    private lateinit var currentDate: MutableState<Calendar>

    private lateinit var navController: NavController


    private val weekDays =
        arrayOf("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela")

    private val calendarHeader = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "Sb", "Nd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

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
    fun WeekFragmentLayout() {

        navController = findNavController()

        currentWeek = remember { mutableStateOf(getCurrentWeek(Calendar.getInstance())) }
        showPeriodDialog = remember { mutableStateOf(false) }
        showDayDialog = remember { mutableStateOf(false) }
        showWeekView = remember { mutableStateOf(true) }
        month = remember { mutableStateOf(Calendar.getInstance()[Calendar.MONTH]) }
        year = remember { mutableStateOf(Calendar.getInstance()[Calendar.YEAR]) }

        currentDate = remember { mutableStateOf((Calendar.getInstance()))}

        val date = Calendar.getInstance()
        date.set(Calendar.MONTH, month.value)
        date.set(Calendar.DATE, 1)
        date.set(Calendar.YEAR, year.value)

        currentMonth = remember { mutableStateOf(getCurrentMonth(date)) }

        var calendarViewStr: String = if (showWeekView.value) "Tydzień" else "Miesiąc"

        val period =
            "${currentWeek.value[0]?.get(Calendar.DAY_OF_MONTH)}.${
                currentWeek.value[0]?.get(
                    Calendar.MONTH
                ) + 1
            }.${
                currentWeek.value[0]?.get(Calendar.YEAR)
            } - ${currentWeek.value[6]?.get(Calendar.DAY_OF_MONTH)}.${
                currentWeek.value[6]?.get(
                    Calendar.MONTH
                ) + 1
            }.${
                currentWeek.value[6]?.get(Calendar.YEAR)
            }"

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

                        IconButton(onClick = { showPeriodDialog.value = true }) {
                            Icon(
                                Icons.Default.KeyboardArrowRight,
                                contentDescription = "Right button",
                                tint = Color.Black
                            )
                        }
                    }

                    WeekView(period)
                    MonthView()


                }


            }
        }

    }

    @Composable
    fun WeekView(period: String) {
        if (showWeekView.value) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffcc4c80.toInt()))
            ) {

                IconButton(onClick = { goToPreviousWeek() }) {
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

                IconButton(onClick = { goToNextWeek() }) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Right button",
                        tint = Color.White
                    )
                }

            }

            DaysList()
        }

    }

    @ExperimentalFoundationApi
    @Composable
    fun MonthView() {
        if (!showWeekView.value) {
            val monthStr: String = "${month.value + 1}.${year.value}"

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffcc4c80.toInt()))
            ) {

                IconButton(onClick = { goToPreviousMonth() }) {
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

                IconButton(onClick = { goToNextMonth() }) {
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
    fun CalendarGrid() {
        val calendarItems = currentMonth.value

        LazyVerticalGrid(
            cells = GridCells.Fixed(7)
        ) {
            items(calendarItems.size) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = calendarItems[it],
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }


    @Composable
    fun ChoosePeriodDialog() {
        if (showPeriodDialog.value) {
            Dialog(onDismissRequest = { showPeriodDialog.value = false }) {
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
    fun ClearButton() {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(12.dp)
        ) {
            IconButton(onClick = { showPeriodDialog.value = false }) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Exit dialog button",
                    tint = Color.Black
                )
            }
        }
    }

    @Composable
    fun DaysList() {
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(7) {
                DaysListItem(currentWeek.value[it], it)
            }
        }
    }


    @Composable
    fun DaysListItem(date: Calendar, index: Int) {


        val bkgColor = if (isDateSame(date, calendar)) {
            Color(0xff52bcff.toInt())
        } else {
            Color.White
        }

        val txtColor = if (date.before(calendar)) {
            Color.Gray
        } else if (isDateSame(date, calendar)) {
            Color.White
        } else {
            Color.Black
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

    private fun getCurrentMonth(firstDay: Calendar): List<String> {
        val dayOfTheWeek = firstDay[Calendar.DAY_OF_WEEK]
        val daysNumber = firstDay.getActualMaximum(Calendar.DAY_OF_MONTH)

        var offset: Int = when {
            dayOfTheWeek == 2 -> {
                0
            }
            dayOfTheWeek > 2 -> {
                dayOfTheWeek - 2
            }
            else -> {
                6
            }
        }

        var offsetList = listOf<String>()
        val numbers = (1..daysNumber).toList().map { it.toString() }

        repeat(offset) {
            offsetList += ""
        }

        return (calendarHeader + offsetList + numbers)

    }

    private fun getCurrentWeek(date: Calendar): Array<Calendar> {
        val dayOfTheWeek = date[Calendar.DAY_OF_WEEK]

        when {
            dayOfTheWeek == 2 -> {
            }
            dayOfTheWeek > 2 -> {
                date.add(Calendar.DAY_OF_MONTH, -(dayOfTheWeek - 2))
            }
            else -> {
                date.add(Calendar.DAY_OF_MONTH, -6)
            }
        }

        var weekArray = arrayOf<Calendar>()
        weekArray += date.clone() as Calendar

        for (i in 1..6) {
            date.add(Calendar.DAY_OF_MONTH, 1)
            weekArray += date.clone() as Calendar
        }

        return weekArray

    }

    private fun goToPreviousWeek() {
        val weekPrev = currentWeek.value[0].clone() as Calendar
        weekPrev.add(Calendar.DAY_OF_MONTH, -7)
        currentWeek.value = getCurrentWeek(weekPrev)
    }

    private fun goToNextWeek() {
        val weekNext = currentWeek.value[6].clone() as Calendar
        weekNext.add(Calendar.DAY_OF_MONTH, 1)
        currentWeek.value = getCurrentWeek(weekNext)
    }

    private fun goToPreviousMonth() {
        currentDate.value.add(Calendar.MONTH, -1)
        currentDate.value.set(Calendar.DATE, 1)
        currentMonth.value = getCurrentMonth(currentDate.value)
        month.value = currentDate.value[Calendar.MONTH]
        year.value = currentDate.value[Calendar.YEAR]
    }

    private fun goToNextMonth() {
        currentDate.value.add(Calendar.MONTH, 1)
        currentDate.value.set(Calendar.DATE, 1)
        currentMonth.value = getCurrentMonth(currentDate.value)
        month.value = currentDate.value[Calendar.MONTH]
        year.value = currentDate.value[Calendar.YEAR]
    }

    @Composable
    fun PeriodFragmentLayout() {

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

                val bColorWeekBtn = remember { mutableStateOf(0xff52bcff) }
                val bColorMonthBtn = remember { mutableStateOf(0xffffffff) }
                val txtColorWeekBtn = remember { mutableStateOf(0xffffffff) }
                val txtColorMonthBtn = remember { mutableStateOf(0xff000000) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(bColorWeekBtn.value.toInt()))
                        .clickable(onClick = {
                            bColorWeekBtn.value = 0xff52bcff
                            bColorMonthBtn.value = 0xffffffff
                            txtColorWeekBtn.value = 0xffffffff
                            txtColorMonthBtn.value = 0xff000000
                            weekClicked.value = true
                        })
                ) {
                    Text(
                        stringResource(R.string.week),
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color(txtColorWeekBtn.value.toInt()),
                            fontSize = 20.sp,
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(bColorMonthBtn.value.toInt()))
                        .clickable(onClick = {
                            bColorMonthBtn.value = 0xff52bcff
                            bColorWeekBtn.value = 0xffffffff
                            txtColorMonthBtn.value = 0xffffffff
                            txtColorWeekBtn.value = 0xff000000
                            weekClicked.value = false
                        })
                ) {
                    Text(
                        stringResource(R.string.month),
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            color = Color(txtColorMonthBtn.value.toInt()),
                            fontSize = 20.sp,
                        )
                    )
                }

            }
            Column {
                Button(
                    onClick = {
                        showWeekView.value = weekClicked.value
                        showPeriodDialog.value = false
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
                    onClick = { showPeriodDialog.value = false },
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeekFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
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


