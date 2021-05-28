package com.intive.calendar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.fragments.CalendarHomeFragmentDirections
import com.intive.calendar.screens.CalendarHeader
import com.intive.calendar.utils.*
import com.intive.shared.EventParcelable
import com.intive.shared.getDateString
import com.intive.shared.getFullDateString
import java.util.*


@ExperimentalFoundationApi
@Composable
fun MonthView(
    navController: NavController,
    showWeekView: Boolean?,
    headerMonth: String?,
    currentMonth: CurrentMonth?,
    monthEvents: List<Day>?,
    goToPreviousMonth: () -> Unit,
    goToNextMonth: () -> Unit,
) {

    if (showWeekView == false) {
        CalendarHeader(
            period = headerMonth!!,
            onClickPrev = { goToPreviousMonth() },
            onClickNext = { goToNextMonth() })
        CalendarGrid(
            navController = navController,
            currentMonth = currentMonth!!,
            monthEvents = monthEvents
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun CalendarGrid(
    navController: NavController,
    currentMonth: CurrentMonth,
    monthEvents: List<Day>?,
) {

    val itemsList: List<Any> = currentMonth.header + currentMonth.offset + currentMonth.days

    val rowsNumber = kotlin.math.ceil(((itemsList.size).toDouble() / 7.0))
    val items: Array<Array<Any>> = Array(rowsNumber.toInt()) { Array(7) { "" } }

    var itemIndex = 0
    outerLoop@ for (i in 0 until rowsNumber.toInt()) {
        for (j in 0..6) {
            if (itemIndex > itemsList.size - 1) {
                break@outerLoop
            }
            items[i][j] = itemsList[itemIndex]
            itemIndex++
        }
    }

    (0 until rowsNumber.toInt()).forEach { row ->
        Row(modifier = Modifier.fillMaxWidth()) {
            items[row].forEach {
                if (it is Calendar) {

                    var bgColor = Color.White
                    var txtColor = Color.Black
                    val onClick: () -> Unit

                    if (isDateSame(it, Calendar.getInstance())) {
                        bgColor = MaterialTheme.colors.secondary
                        txtColor = Color.White
                    } else if (it.before(Calendar.getInstance())) {
                        txtColor = Color.Gray
                    }

                    var dayColumnModifier = Modifier.background(bgColor)

                    if (monthEvents!!.find { event -> event.date == getDateString(it) } != null) {

                        val index =
                            monthEvents.indexOfFirst { event -> event.date!! == getDateString(it) }

                        if (!(isDateSame(it, Calendar.getInstance()))) {
                            bgColor = colorResource(R.color.pale_blue)
                        }

                        when {
                            monthEvents[index].events!!.size == 1 -> {
                                val isDayActive =
                                    !it.before(Calendar.getInstance())

                                val eventParcelable = EventParcelable(
                                    id = monthEvents[index].events!![0].id,
                                    date = getFullDateString(it),
                                    time = "${monthEvents[index].events!![0].timeStart} - ${monthEvents[index].events!![0].timeEnd}",
                                    name = monthEvents[index].events!![0].name,
                                    inviteResponse = monthEvents[index].events!![0].inviteResponse,
                                    users = monthEvents[index].events!![0].users,
                                    active = isDayActive

                                )

                                val directions =
                                    CalendarHomeFragmentDirections.actionCalendarFragmentToEventFragment(
                                        eventInfoParcelable = eventParcelable
                                    )

                                onClick =
                                    {
                                        navController.navigate(
                                            directions
                                        )
                                    }

                                dayColumnModifier = Modifier
                                    .background(bgColor)
                                    .clickable(onClick = onClick)
                            }
                            monthEvents[index].events!!.size > 1 -> {
                                val isDayActive =
                                    !(it.before(Calendar.getInstance()))

                                val dayParcelable = DayParcelable(
                                    date = getFullDateString(it),
                                    events = monthEvents[index].events!!,
                                    active = isDayActive
                                )

                                val directions =
                                    CalendarHomeFragmentDirections.actionCalendarFragmentToDayFragment(
                                        dayInfo = dayParcelable
                                    )

                                onClick =
                                    {
                                        navController.navigate(directions)
                                    }

                                dayColumnModifier = Modifier
                                    .background(bgColor)
                                    .clickable(onClick = onClick)
                            }
                            else -> {
                                dayColumnModifier = Modifier
                                    .background(bgColor)
                            }
                        }

                    }


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = dayColumnModifier.weight(1f)
                    ) {
                        Text(
                            text = it[Calendar.DAY_OF_MONTH].toString(),
                            style = TextStyle(fontSize = 18.sp, color = txtColor),
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = it.toString(),
                            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(54.dp))

}