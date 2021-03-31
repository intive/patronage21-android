package com.example.patron_a_tive.calendar_module.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CalendarHomeViewModel : ViewModel() {
    private val calendarHeader = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "Sb", "Nd")

    private val _currentWeek =
        MutableLiveData<Array<Calendar>>(getCurrentWeek(Calendar.getInstance()))
    val currentWeek: LiveData<Array<Calendar>> = _currentWeek

    private val _month = MutableLiveData<Int>(Calendar.getInstance()[Calendar.MONTH])
    val month: LiveData<Int> = _month

    private val _year = MutableLiveData<Int>(Calendar.getInstance()[Calendar.YEAR])
    val year: LiveData<Int> = _year

    private val currentDate: Calendar = Calendar.getInstance()


    private val _currentMonth = MutableLiveData<List<String>>(getCurrentMonth(getFirstDay()))
    var currentMonth: LiveData<List<String>> = _currentMonth

    private val _showPeriodDialog = MutableLiveData<Boolean>(false)
    val showPeriodDialog: LiveData<Boolean> = _showPeriodDialog

    private val _showWeekView = MutableLiveData<Boolean>(true)
    val showWeekView: LiveData<Boolean> = _showWeekView


    private val _bColorWeekBtn = MutableLiveData<Long>(0xff52bcff)
    val bColorWeekBtn: LiveData<Long> = _bColorWeekBtn

    private val _bColorMonthBtn = MutableLiveData<Long>(0xffffffff)
    val bColorMonthBtn: LiveData<Long> = _bColorMonthBtn

    private val _txtColorWeekBtn = MutableLiveData<Long>(0xffffffff)
    val txtColorWeekBtn: LiveData<Long> = _txtColorWeekBtn

    private val _txtColorMonthBtn = MutableLiveData<Long>(0xff000000)
    val txtColorMonthBtn: LiveData<Long> = _txtColorMonthBtn


    fun getCurrentWeek(date: Calendar): Array<Calendar>? {
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

    fun goToPreviousWeek() {
        val weekPrev = currentWeek.value?.get(0)?.clone() as Calendar
        weekPrev.add(Calendar.DAY_OF_MONTH, -7)
        _currentWeek.value = getCurrentWeek(weekPrev)
    }

    fun goToNextWeek() {
        val weekNext = currentWeek.value?.get(6)?.clone() as Calendar
        weekNext.add(Calendar.DAY_OF_MONTH, 1)
        _currentWeek.value = getCurrentWeek(weekNext)
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

    fun setMonth(value: Int) {
        _month.value = value
    }

    fun setYear(value: Int) {
        _year.value = value
    }


    private fun getCurrentMonth(firstDay: Calendar): List<String>? {

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

    fun goToPreviousMonth() {
        currentDate.add(Calendar.MONTH, -1)
        currentDate.set(Calendar.DATE, 1)
        _currentMonth.value = getCurrentMonth(currentDate)
        _month.value = currentDate[Calendar.MONTH]
        _year.value = currentDate[Calendar.YEAR]
    }

    fun goToNextMonth() {
        currentDate.add(Calendar.MONTH, 1)
        currentDate.set(Calendar.DATE, 1)
        _currentMonth.value = getCurrentMonth(currentDate)
        _month.value = currentDate[Calendar.MONTH]
        _year.value = currentDate[Calendar.YEAR]
    }

    private fun getFirstDay(): Calendar {
        var date = Calendar.getInstance()
        _month.value?.let { date.set(Calendar.MONTH, it) }
        date.set(Calendar.DATE, 1)
        _year.value?.let { date.set(Calendar.YEAR, it) }
        return date
    }

    fun weekClicked(){
        _bColorWeekBtn.value = 0xff52bcff
        _bColorMonthBtn.value = 0xffffffff
        _txtColorWeekBtn.value = 0xffffffff
        _txtColorMonthBtn.value = 0xff000000
    }

    fun monthClicked(){
        _bColorMonthBtn.value = 0xff52bcff
        _bColorWeekBtn.value = 0xffffffff
        _txtColorMonthBtn.value = 0xffffffff
        _txtColorWeekBtn.value = 0xff000000
    }

}