package com.intive.calendar.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.NewEvent
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.*
import java.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AddEventViewModel(private val repository: Repository, private val dispatchers: DispatcherProvider) : ViewModel() {


    private val addEventChannel = Channel<AddNewEvent>()
    val addEventFlow = addEventChannel.receiveAsFlow()

    private fun showSnackbar(errorType: AddNewEvent) = viewModelScope.launch {
        addEventChannel.send(errorType)
    }

    private val c: Calendar = Calendar.getInstance()
    private val hour = c[Calendar.HOUR_OF_DAY]

    private val _date = MutableLiveData(Calendar.getInstance())
    val date: LiveData<Calendar> = _date

    private val _hourStart = MutableLiveData("$hour")
    var hourStart: LiveData<String> = _hourStart

    private val _hourEnd = MutableLiveData("$hour")
    var hourEnd: LiveData<String> = _hourEnd

    private val _minutesStart = MutableLiveData("00")
    var minutesStart: LiveData<String> = _minutesStart

    private val _minutesEnd = MutableLiveData("00")
    var minutesEnd: LiveData<String> = _minutesEnd

    private val _inputValue = MutableLiveData("")
    var inputValue: LiveData<String> = _inputValue

    private val _technologyGroups: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    var technologyGroups: LiveData<List<String>> = _technologyGroups

    private val _selectedTechnologyGroups = mutableListOf<String>()


    fun updateSelectedTechnologyGroups(technologyGroup: String) {
        if (technologyGroup in _selectedTechnologyGroups) {
            _selectedTechnologyGroups.remove(technologyGroup)
        } else {
            _selectedTechnologyGroups.add(technologyGroup)
        }
    }

    private val handler = CoroutineExceptionHandler { _, e -> e.printStackTrace() }

    init {
        var technologyGroupsList: List<String>

        viewModelScope.launch(dispatchers.io + handler) {
            technologyGroupsList = repository.getTechnologies()
            _technologyGroups.postValue(technologyGroupsList)
        }
    }


    fun setInputValue(value: String) {
        _inputValue.value = value
    }

    fun setDate(value: Calendar) {
        _date.value = value
    }

    fun setTimeStart(hour: Int, minutes: Int) {

        val (hourString, minutesString) = formatTime(hour, minutes)

        _hourStart.value = hourString
        _minutesStart.value = minutesString
    }

    fun setTimeEnd(hour: Int, minutes: Int) {

        val (hourString, minutesString) = formatTime(hour, minutes)

        _hourEnd.value = hourString
        _minutesEnd.value = minutesString
    }

    private fun isDateValid(): Boolean {
        val today: Calendar = Calendar.getInstance()
        val startDate = _date.value?.clone() as Calendar
        _hourStart.value?.let { _date.value?.set(Calendar.HOUR, it.toInt()) }
        _minutesStart.value?.let { _date.value?.set(Calendar.MINUTE, it.toInt()) }
        return today.before(_date.value) && !startDate.before(Calendar.getInstance())
    }

    private fun isTimeValid(): Boolean {

        val endDate = _date.value?.clone() as Calendar
        _hourEnd.value?.let { endDate.set(Calendar.HOUR, it.toInt()) }
        _minutesEnd.value?.let { endDate.set(Calendar.MINUTE, it.toInt()) }
        return !endDate.before(_date.value)
    }

    private fun areCheckboxesValid(): Boolean {
        return _selectedTechnologyGroups.size >= 1
    }

    private fun isInputValid(): Boolean {
        return _inputValue.value != ""
    }

    fun isFormValid(popBackStack: () -> Boolean, refreshCalendar: () -> Unit) {
        if (!isInputValid()) {
            showSnackbar(AddNewEvent.InvalidInput)
        } else if (!isDateValid()) {
            showSnackbar(AddNewEvent.InvalidDate)
        } else if (!isTimeValid()) {
            showSnackbar(AddNewEvent.InvalidTime)
        } else if (!areCheckboxesValid()) {
            showSnackbar(AddNewEvent.InvalidCheckboxes)
        } else {
            addNewEvent(
                date = getDateString(_date.value!!),
                timeStart = timeToString(_hourStart.value!!, _minutesStart.value!!),
                timeEnd = timeToString(_hourEnd.value!!, _minutesEnd.value!!),
                name = _inputValue.value!!,
                refreshCalendar = { refreshCalendar() },
                popBackStack = { popBackStack() },
            )
        }
    }

    private fun addNewEvent(
        date: String,
        timeStart: String,
        timeEnd: String,
        name: String,
        refreshCalendar: () -> Unit,
        popBackStack: () -> Boolean
    ) {

        val newEvent =
            NewEvent(date, timeStart, timeEnd, name, _selectedTechnologyGroups.toString())
        val handler = CoroutineExceptionHandler { _, _ ->
            showSnackbar(AddNewEvent.Error)
        }


        var response: Response<String>

        viewModelScope.launch(handler) {

            withContext(dispatchers.io) {
                response = repository.addNewEvent(newEvent)
            }

            if (response.isSuccessful) {
                refreshCalendar()
                popBackStack()
            } else {
                showSnackbar(AddNewEvent.Error)
            }
        }

    }
}