package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.EditEvent
import com.intive.repository.domain.model.NewEvent
import com.intive.repository.util.DispatcherProvider
import com.intive.shared.getDateAndTimeString
import com.intive.shared.getDateString
import kotlinx.coroutines.*
import java.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AddEditEventViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {


    private val addEventChannel = Channel<EventChannel>()
    val addEventFlow = addEventChannel.receiveAsFlow()

    private fun showSnackbar(errorType: EventChannel) = viewModelScope.launch {
        addEventChannel.send(errorType)
    }

    private val c: Calendar = Calendar.getInstance()
    private var hour = c[Calendar.HOUR_OF_DAY]

    private val _dateStart = MutableLiveData(Calendar.getInstance())
    val dateStart: LiveData<Calendar> = _dateStart

    private val _dateEnd = MutableLiveData(Calendar.getInstance())
    val dateEnd: LiveData<Calendar> = _dateEnd

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

    private val _descriptionValue = MutableLiveData("")
    var descriptionValue: LiveData<String> = _descriptionValue

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

    fun setDescriptionValue(value: String) {
        _descriptionValue.value = value
    }

    fun setStartDate(value: Calendar) {
        _dateStart.value = value
    }

    fun setEndDate(value: Calendar) {
        _dateEnd.value = value
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
        val today = Calendar.getInstance()
        val startDate = _dateStart.value?.clone() as Calendar
        _hourStart.value?.let { startDate.set(Calendar.HOUR_OF_DAY, it.toInt()) }
        _minutesStart.value?.let { startDate.set(Calendar.MINUTE, it.toInt()) }
        val endDate = _dateEnd.value?.clone() as Calendar
        _hourEnd.value?.let { endDate.set(Calendar.HOUR_OF_DAY, it.toInt()) }
        _minutesEnd.value?.let { endDate.set(Calendar.MINUTE, it.toInt()) }

        return !startDate.before(today) && endDate.after(startDate)
    }

    private fun areCheckboxesValid(): Boolean {
        return _selectedTechnologyGroups.size >= 1
    }

    private fun isInputValid(): Boolean {
        return _inputValue.value != ""
    }

    private fun isFormValid(): Boolean {
        if (!isInputValid()) {
            showSnackbar(EventChannel.InvalidInput)
        } else if (!isDateValid()) {
            showSnackbar(EventChannel.InvalidDate)
        } else if (!areCheckboxesValid()) {
            showSnackbar(EventChannel.InvalidCheckboxes)
        } else {
            return true
        }
        return false
    }

    fun addNewEvent(
        refreshEventsList: () -> Unit,
        popBackStack: () -> Boolean
    ) {

        val name = _inputValue.value!!
        val description = _descriptionValue.value!!
        val timeStart = "${_hourStart.value!!}:${_minutesStart.value!!}:00"
        val timeEnd = "${_hourEnd.value!!}:${_minutesEnd.value!!}:00"
        val dateStart = getDateAndTimeString(_dateStart.value!!, timeStart)
        val dateEnd = getDateAndTimeString(_dateEnd.value!!, timeEnd)

        if (isFormValid()) {
            val newEvent = NewEvent(name, description, dateStart, dateEnd)
            val handler = CoroutineExceptionHandler { _, _ ->
                showSnackbar(EventChannel.AddEventError)
            }

            var response: Response<Any>

            viewModelScope.launch(handler) {

                withContext(dispatchers.io) {
                    response = repository.addNewEvent(newEvent)
                }

                if (response.isSuccessful) {
                    showSnackbar(EventChannel.AddEventSuccess)
                    refreshEventsList()
                    popBackStack()
                } else {
                    showSnackbar(EventChannel.AddEventError)
                }
            }
        }

    }

    fun editEvent(
        refreshEventsList: () -> Unit,
        popBackStack: () -> Unit,
        id: Long
    ) {
        val date = getDateString(_dateStart.value!!)
        val timeStart = timeToString(_hourStart.value!!, _minutesStart.value!!)
        val timeEnd = timeToString(_hourEnd.value!!, _minutesEnd.value!!)
        val name = _inputValue.value!!

        if (isFormValid()) {
            val editEvent =
                EditEvent(date, timeStart, timeEnd, name, _selectedTechnologyGroups.toString())
            val handler = CoroutineExceptionHandler { _, _ ->
                showSnackbar(EventChannel.EditEventError)
            }

            var response: Response<String>

            viewModelScope.launch(handler) {

                withContext(dispatchers.io) {
                    response = repository.editEvent(editEvent, id)
                }

                if (response.isSuccessful) {
                    showSnackbar(EventChannel.EditEventSuccess)
                    refreshEventsList()
                    popBackStack()
                    popBackStack()
                } else {
                    showSnackbar(EventChannel.EditEventError)
                }
            }
        }

    }

}