package com.intive.calendar.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.NewEvent
import kotlinx.coroutines.*
import java.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEventViewModel(private val repository: Repository) : ViewModel() {


    private val addEventChannel = Channel<AddNewEvent>()
    val addEventFlow = addEventChannel.receiveAsFlow()

    private fun showSnackbar(errorType: AddNewEvent) = viewModelScope.launch {
        addEventChannel.send(errorType)
    }

    private val c: Calendar = Calendar.getInstance()
    private val hour = c[Calendar.HOUR_OF_DAY]

    private val _date = MutableLiveData(Calendar.getInstance())
    val date: LiveData<Calendar> = _date

    private val _hourStart = MutableLiveData("${hour + 1}")
    var hourStart: LiveData<String> = _hourStart

    private val _hourEnd = MutableLiveData("${hour + 2}")
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

        viewModelScope.launch(Dispatchers.IO + handler) {
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

    private fun validateDate(): Boolean {
        val today: Calendar = Calendar.getInstance()
        _hourStart.value?.let { _date.value?.set(Calendar.HOUR, it.toInt()) }
        _minutesStart.value?.let { _date.value?.set(Calendar.MINUTE, it.toInt()) }
        return today.before(_date.value)
    }

    private fun validateTime(): Boolean {
        val endDate = _date.value?.clone() as Calendar
        _hourEnd.value?.let { endDate.set(Calendar.HOUR, it.toInt()) }
        _minutesEnd.value?.let { endDate.set(Calendar.MINUTE, it.toInt()) }
        return !endDate.before(_date.value)
    }

    private fun validateCheckboxes(): Boolean {
        return _selectedTechnologyGroups.size in 1..3
    }

    private fun validateInput(): Boolean {
        return _inputValue.value != ""
    }

    fun validateForm(context: Context, popBackStack: () -> Boolean, refreshCalendar: () -> Unit) {
        if (!validateInput()) {
            showSnackbar(AddNewEvent.InvalidInput)
        } else if (!validateDate()) {
            showSnackbar(AddNewEvent.InvalidDate)
        } else if (!validateTime()) {
            showSnackbar(AddNewEvent.InvalidTime)
        } else if (!validateCheckboxes()) {
            showSnackbar(AddNewEvent.InvalidCheckboxes)
        } else {
            addNewEvent(
                date = getDateString(_date.value!!),
                timeStart = timeToString(_hourStart.value!!, _minutesStart.value!!),
                timeEnd = timeToString(_hourEnd.value!!, _minutesEnd.value!!),
                name = _inputValue.value!!,
                context = context,
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
        context: Context,
        refreshCalendar: () -> Unit,
        popBackStack: () -> Boolean
    ) {

        val newEvent = NewEvent(date, timeStart, timeEnd, name, _selectedTechnologyGroups)
        val handler = CoroutineExceptionHandler { _, _ ->
            showSnackbar(AddNewEvent.Error)
        }

        if (isOnline(context)) {

            viewModelScope.launch(handler) {

                withContext(Dispatchers.IO) {
                    repository.addNewEvent(newEvent)
                }

                refreshCalendar()
                popBackStack()

            }
        } else {
            showSnackbar(AddNewEvent.Error)
        }
    }
}