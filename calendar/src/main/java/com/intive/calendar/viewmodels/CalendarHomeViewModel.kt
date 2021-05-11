package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.*
import com.intive.repository.Repository
import com.intive.calendar.utils.Day
import com.intive.repository.domain.model.Event
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.*
import java.util.*

class CalendarHomeViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e -> e.printStackTrace() }

    private val _weekEvents: MutableLiveData<List<Day>> =
        MutableLiveData(List(7) { Day(null, null) })
    val weekEvents: LiveData<List<Day>> = _weekEvents

    private val _monthEvents: MutableLiveData<List<Day>> =
        MutableLiveData(listOf(Day(null, emptyList())))
    val monthEvents: LiveData<List<Day>> = _monthEvents

    private val _currentWeek = MutableLiveData(getCurrentWeek(Calendar.getInstance()))
    val currentWeek: LiveData<Array<Calendar>> = _currentWeek

    private val _monthHeader = MutableLiveData("")
    val monthHeader: LiveData<String> = _monthHeader

    private val _weekHeader = MutableLiveData(setWeekHeader())
    val weekHeader: LiveData<String> = _weekHeader

    private var currentDate: Calendar = Calendar.getInstance()

    private val _currentMonth = MutableLiveData(getCurrentMonth())
    var currentMonth: LiveData<CurrentMonth> = _currentMonth

    private val _showWeekView = MutableLiveData(true)
    val showWeekView: LiveData<Boolean> = _showWeekView


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

        getWeekEvents(getDateString(weekArray[0]), getDateString(weekArray[6]))
        return weekArray
    }

    private fun getCurrentMonth(): CurrentMonth {

        currentDate.set(Calendar.DATE, 1)
        val dayOfTheWeek = currentDate[Calendar.DAY_OF_WEEK]
        val daysNumber = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH)
        val date = currentDate.clone() as Calendar

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

        val numbers = mutableListOf<Calendar>()
        numbers += date.clone() as Calendar

        for (i in 1 until daysNumber) {
            date.add(Calendar.DAY_OF_MONTH, 1)
            numbers += date.clone() as Calendar
        }

        val offsetList = List(offset) { "" }

        _monthHeader.value = getMonthAndYearString(currentDate)

        getMonthEvents(getDateString(numbers.first()), getDateString(numbers.last()))
        return CurrentMonth(offset = offsetList, days = numbers)

    }

    private fun getMonthEvents(dateStart: String, dateEnd: String) {

        var events: List<Event>
        val monthArray = mutableListOf<Day>()

        viewModelScope.launch(dispatchers.io + handler) {

            _monthEvents.postValue(listOf(Day(null, emptyList())))

            events = repository.getEvents(dateStart, dateEnd, userId)

            for (i in events.indices) {
                val index = monthArray.indexOfFirst { it.date!! == events[i].date }
                if (index != -1) {
                    monthArray[index].events = monthArray[index].events?.plus(events[i])
                } else {
                    monthArray += Day(events[i].date, listOf(events[i]))
                }
            }

            _monthEvents.postValue(monthArray)
        }
    }


    private fun getWeekEvents(dateStart: String, dateEnd: String) {

        var events: List<Event>
        val weekArray = mutableListOf<Day>()

        viewModelScope.launch(dispatchers.io + handler) {
            _weekEvents.postValue(listOf(Day(null, emptyList())))


            events = repository.getEvents(dateStart, dateEnd, userId)

            for (i in events.indices) {
                val index = weekArray.indexOfFirst { it.date!! == events[i].date }
                if (index != -1) {
                    weekArray[index].events = weekArray[index].events?.plus(events[i])
                } else {
                    weekArray += Day(events[i].date, listOf(events[i]))
                }
            }

            _weekEvents.postValue(weekArray)

        }
    }


    fun onCalendarViewChange(value: String) {
        if (value == calendarWeekViewLabel) {
            refreshCalendar()
            showWeekView()
        } else {
            refreshCalendar()
            showMonthView()
        }
    }

    fun goToPreviousWeek() {
        val weekPrev = currentWeek.value?.get(0)
        weekPrev?.add(Calendar.DAY_OF_MONTH, -7)
        _currentWeek.value = weekPrev?.let { getCurrentWeek(it) }
        _weekHeader.value = setWeekHeader()
    }

    fun goToNextWeek() {
        val weekNext = currentWeek.value?.get(6)
        weekNext?.add(Calendar.DAY_OF_MONTH, 1)
        _currentWeek.value = weekNext?.let { getCurrentWeek(it) }
        _weekHeader.value = setWeekHeader()
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

    private fun showWeekView() {
        _showWeekView.value = true
    }

    private fun showMonthView() {
        _showWeekView.value = false
    }


    fun refreshCalendar() {
        _currentWeek.value = getCurrentWeek(Calendar.getInstance())
        _weekHeader.value = setWeekHeader()
        currentDate = Calendar.getInstance()
        _currentMonth.value = getCurrentMonth()
    }

    private fun setWeekHeader(): String {
        return "${
            getDateString(
                _currentWeek.value!![0],
                "."
            )
        } - ${getDateString(_currentWeek.value!![6], ".")}"
    }
}