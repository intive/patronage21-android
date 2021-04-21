package com.intive.calendar.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.Day
import com.intive.repository.domain.model.DayWeek
import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.Event2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*

class CalendarHomeViewModel(private val repository: Repository) : ViewModel() {

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
        Day(
            10,
            listOf(Event(5, "12:00-13:00", "Retrospective"), Event(6, "13:00-14:00", "Planning"))
        ),
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
        Day(
            28,
            listOf(Event(11, "12:00-13:00", "Retrospective"), Event(12, "13:00-14:00", "Planning"))
        ),
        Day(29, emptyList()),
        Day(30, emptyList())
    )


    private fun getWeekEvents(calendar: Calendar, dateStart: String, dateEnd: String) {

        var events: List<Event2>?
        val weekArray = mutableListOf<DayWeek>()
        weekArray += DayWeek(calendar.clone() as Calendar, emptyList())

        for (i in 1..6) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            weekArray += DayWeek(calendar.clone() as Calendar, emptyList())
        }

        viewModelScope.launch(Dispatchers.IO) {
            _weekEvents.postValue(List(7){DayWeek(null, null)})
            try {

                events = repository.getEvents(dateStart, dateEnd)

                for (i in weekArray.indices) {
                    for(j in events!!.indices){
                        if(getDateString(weekArray[i].date!!) == events!![j].date){
                            weekArray[i].events = weekArray[i].events?.plus(events!![j])
                        }
                    }
                    _weekEvents.postValue(weekArray)
                }

            } catch (e: KotlinNullPointerException) {
            } catch (e: HttpException) {
            }

        }
    }

    private fun getDateString(date: Calendar): String {
        return "${date[Calendar.DAY_OF_MONTH]}-0${date[Calendar.MONTH] + 1}-${date[Calendar.YEAR]}"
    }


    private val _weekEvents: MutableLiveData<List<DayWeek>> = MutableLiveData(List(7){DayWeek(null, null)})
    val weekEvents: LiveData<List<DayWeek>> = _weekEvents

    private val _currentWeek = MutableLiveData(getCurrentWeek(Calendar.getInstance()))
    val currentWeek: LiveData<Array<Calendar>> = _currentWeek

    private val _weekClicked = MutableLiveData(true)
    val weekClicked: LiveData<Boolean> = _weekClicked

    private val _monthHeader = MutableLiveData("")
    val monthHeader: LiveData<String> = _monthHeader

    private val _weekHeader = MutableLiveData(setWeekHeader())
    val weekHeader: LiveData<String> = _weekHeader

    private var currentDate: Calendar = Calendar.getInstance()

    private val _currentMonth = MutableLiveData(getCurrentMonth())
    var currentMonth: LiveData<List<Any>> = _currentMonth

    private val _showPeriodDialog = MutableLiveData(false)
    val showPeriodDialog: LiveData<Boolean> = _showPeriodDialog

    private val _showWeekView = MutableLiveData(true)
    val showWeekView: LiveData<Boolean> = _showWeekView

    private val _bColorWeekBtn = MutableLiveData(colorBlue)
    val bColorWeekBtn: LiveData<Long> = _bColorWeekBtn

    private val _bColorMonthBtn = MutableLiveData(colorWhite)
    val bColorMonthBtn: LiveData<Long> = _bColorMonthBtn

    private val _txtColorWeekBtn = MutableLiveData(colorWhite)
    val txtColorWeekBtn: LiveData<Long> = _txtColorWeekBtn

    private val _txtColorMonthBtn = MutableLiveData(colorBlack)
    val txtColorMonthBtn: LiveData<Long> = _txtColorMonthBtn


    fun setCurrentWeek() {
        _currentWeek.value = getCurrentWeek(Calendar.getInstance())
    }

    fun setCurrentMonth() {
        currentDate = Calendar.getInstance()
        _currentMonth.value = getCurrentMonth()
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

        val firstDay = date.clone() as Calendar
        var weekArray = arrayOf<Calendar>()
        weekArray += date.clone() as Calendar

        for (i in 1..6) {
            date.add(Calendar.DAY_OF_MONTH, 1)
            weekArray += date.clone() as Calendar
        }

        /*
        runBlocking {

            val job: Job = launch(context = Dispatchers.IO) {

                try {
                    events = repository.getEvents("1-04-2021", "30-04-2021")
                    /*
                    Log.d("kliku", events.toString())

                    for (i in 1 until events!!.size) {
                        for(j in 1 until _currentWeek.value!!.size){
                            if(getDateString(_currentWeek.value!![j].date) == events!![i].date){
                                Log.d("klikuIN", "xyz")
                                _currentWeek.value!![j].events += events!![i]
                            }
                        }
                    }

                     */

                } catch (e: KotlinNullPointerException) {
                } catch (e: HttpException) {
                }
            }

            job.join()
        }

         */

        getWeekEvents(firstDay, getDateString(weekArray[0]), getDateString(weekArray[6]))
        return weekArray
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
        _bColorWeekBtn.value = colorBlue
        _bColorMonthBtn.value = colorWhite
        _txtColorWeekBtn.value = colorWhite
        _txtColorMonthBtn.value = colorBlack
        _weekClicked.value = true
    }

    fun monthClicked() {
        _bColorMonthBtn.value = colorBlue
        _bColorWeekBtn.value = colorWhite
        _txtColorMonthBtn.value = colorWhite
        _txtColorWeekBtn.value = colorBlack
        _weekClicked.value = false
    }

    private fun setWeekHeader(): String {
        return "${_currentWeek.value!![0][Calendar.DAY_OF_MONTH]}.${
            _currentWeek.value!![0][Calendar.MONTH].plus(1)
        }.${_currentWeek.value!![0][Calendar.YEAR]}" +
                "-${_currentWeek.value!![6][Calendar.DAY_OF_MONTH]}.${
                    _currentWeek.value!![6][Calendar.MONTH].plus(1)
                }.${_currentWeek.value!![6][Calendar.YEAR]}"
    }
}

