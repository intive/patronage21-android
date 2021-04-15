package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.calendar.utils.calendarHeader
import java.util.*

class CalendarHomeViewModel : ViewModel() {

    data class Event(
        //val date: Calendar,
        val id: Int,
        val time: String,
        val name: String
    )

    data class Day(val index: Int, val events: List<Event>)
    data class DayWeek(val date: Calendar, val events: List<Event>)

    private val days: List<Day> = listOf(
        Day(0, emptyList()),
        Day(3, listOf(Event(2, "12:00-13:00", "Retrospective"), Event(3, "13:00-14:00", "Planning"))),
        Day(2, listOf(Event(1, "12:00-13:00", "Daily"))),
        Day(3, listOf(Event(2, "12:00-13:00", "Retrospective"), Event(3, "13:00-14:00", "Planning"))),
        Day(4, emptyList()),
        Day(3, listOf(Event(4, "12:00-13:00", "Retrospective"), Event(5, "13:00-14:00", "Planning"))),
        Day(6, emptyList())
    )

    private val monthDays: List<Day> = listOf(
        Day(1, emptyList()),
        Day(2, listOf(Event(1, "12:00-13:00", "Daily"))),
        Day(3, emptyList()),
        Day(4, emptyList()),
        Day(5, emptyList()),
        Day(6, emptyList()),
        Day(7, emptyList()),
        Day(8, emptyList()),
        Day(9, emptyList()),
        Day(10, listOf(Event(5, "12:00-13:00", "Retrospective"), Event(6, "13:00-14:00", "Planning"))),
        Day(11, emptyList()),
        Day(12, emptyList()),
        Day(13, emptyList()),
        Day(14, emptyList()),
        Day(15, emptyList()),
        Day(16, emptyList()),
        Day(17, emptyList()),
        Day(18, emptyList()),
        Day(19, emptyList()),
        Day(20, emptyList()),
        Day(21, emptyList()),
        Day(22, emptyList()),
        Day(23, emptyList()),
        Day(24, emptyList()),
        Day(25, emptyList()),
        Day(26, emptyList()),
        Day(27, emptyList()),
        Day(28, listOf(Event(11, "12:00-13:00", "Retrospective"), Event(12, "13:00-14:00", "Planning"))),
        Day(29, emptyList()),
        Day(30, emptyList())
    )

    private val _currentWeek = MutableLiveData(getCurrentWeek(Calendar.getInstance()))
    val currentWeek: LiveData<Array<DayWeek>> = _currentWeek

    private val _weekClicked = MutableLiveData(true)
    val weekClicked: LiveData<Boolean> = _weekClicked

    private val _monthHeader = MutableLiveData("")
    val monthHeader: LiveData<String> = _monthHeader

    private var currentDate: Calendar = Calendar.getInstance()

    private val _currentMonth = MutableLiveData(getCurrentMonth())
    var currentMonth: LiveData<List<Any>> = _currentMonth

    private val _showPeriodDialog = MutableLiveData(false)
    val showPeriodDialog: LiveData<Boolean> = _showPeriodDialog

    private val _showWeekView = MutableLiveData(true)
    val showWeekView: LiveData<Boolean> = _showWeekView

    private val _bColorWeekBtn = MutableLiveData(0xff52bcff)
    val bColorWeekBtn: LiveData<Long> = _bColorWeekBtn

    private val _bColorMonthBtn = MutableLiveData(0xffffffff)
    val bColorMonthBtn: LiveData<Long> = _bColorMonthBtn

    private val _txtColorWeekBtn = MutableLiveData(0xffffffff)
    val txtColorWeekBtn: LiveData<Long> = _txtColorWeekBtn

    private val _txtColorMonthBtn = MutableLiveData(0xff000000)
    val txtColorMonthBtn: LiveData<Long> = _txtColorMonthBtn


    fun setCurrentWeek() {
        _currentWeek.value = getCurrentWeek(Calendar.getInstance())
    }

    fun setCurrentMonth() {
        currentDate = Calendar.getInstance()
        _currentMonth.value = getCurrentMonth()
    }

    private fun getCurrentWeek(date: Calendar): Array<DayWeek> {
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

        var weekArray = arrayOf<DayWeek>()
        weekArray += DayWeek(date.clone() as Calendar, emptyList())

        for (i in 1..6) {
            date.add(Calendar.DAY_OF_MONTH, 1)
            weekArray += DayWeek(date.clone() as Calendar, days[i].events)
        }

        return weekArray
    }

    fun goToPreviousWeek() {
        val weekPrev = currentWeek.value?.get(0)
        weekPrev?.date?.add(Calendar.DAY_OF_MONTH, -7)
        _currentWeek.value = weekPrev?.let { getCurrentWeek(it.date) }
    }

    fun goToNextWeek() {
        val weekNext = currentWeek.value?.get(6)
        weekNext?.date?.add(Calendar.DAY_OF_MONTH, 1)
        _currentWeek.value = weekNext?.let { getCurrentWeek(it.date) }
    }

    fun showDialog() {
        _showPeriodDialog.value = true
    }

    fun hideDialog() {
        _showPeriodDialog.value = false
    }

    fun showWeekView() {
        _showWeekView.value = true
    }

    fun showMonthView() {
        _showWeekView.value = false
    }


    private fun getCurrentMonth(): List<Any> {
        currentDate.set(Calendar.DATE, 1)
        val dayOfTheWeek = currentDate[Calendar.DAY_OF_WEEK]
        //val daysNumber = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)

        val offset: Int = when {
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

        val offsetList = mutableListOf<String>()
        //val numbers = (1..daysNumber).toList().map { it.toString() }

        val numbers = monthDays

        repeat(offset) {
            offsetList += ""
        }

        _monthHeader.value = "${currentDate[Calendar.MONTH] + 1}.${currentDate[Calendar.YEAR]}"

        return (calendarHeader + offsetList + numbers)

    }

    fun goToPreviousMonth() {
        currentDate.add(Calendar.MONTH, -1)
        currentDate.set(Calendar.DATE, 1)
        _currentMonth.value = getCurrentMonth()
    }

    fun goToNextMonth() {
        currentDate.add(Calendar.MONTH, 1)
        currentDate.set(Calendar.DATE, 1)
        _currentMonth.value = getCurrentMonth()
    }


    fun weekClicked() {
        _bColorWeekBtn.value = 0xff52bcff
        _bColorMonthBtn.value = 0xffffffff
        _txtColorWeekBtn.value = 0xffffffff
        _txtColorMonthBtn.value = 0xff000000
        _weekClicked.value = true
    }

    fun monthClicked() {
        _bColorMonthBtn.value = 0xff52bcff
        _bColorWeekBtn.value = 0xffffffff
        _txtColorMonthBtn.value = 0xffffffff
        _txtColorWeekBtn.value = 0xff000000
        _weekClicked.value = false
    }
}