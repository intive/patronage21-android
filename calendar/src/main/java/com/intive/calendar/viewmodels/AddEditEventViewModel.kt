package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.*
import com.intive.repository.Repository
import com.intive.repository.database.EventLogger
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
    private val dispatchers: DispatcherProvider,
    private val eventLogger: EventLogger
) : ViewModel() {

    private val addEventChannel = Channel<EventChannel>()
    val addEventFlow = addEventChannel.receiveAsFlow()

    private fun showSnackbar(errorType: EventChannel) = viewModelScope.launch {
        addEventChannel.send(errorType)
    }

    private val _dateStart = MutableLiveData(Calendar.getInstance())
    val dateStart: LiveData<Calendar> = _dateStart

    private val _dateEnd = MutableLiveData(Calendar.getInstance())
    val dateEnd: LiveData<Calendar> = _dateEnd

    private val _nameValue = MutableLiveData("")
    var nameValue: LiveData<String> = _nameValue

    private val _descriptionValue = MutableLiveData("")
    var descriptionValue: LiveData<String> = _descriptionValue

    private val _technologyGroups: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    var technologyGroups: LiveData<List<String>> = _technologyGroups

    private val _selectedTechnologyGroups = mutableListOf<String>()

    init {
        val startDateAndTime = _dateStart.value!!.clone() as Calendar
        startDateAndTime.add(Calendar.HOUR_OF_DAY, 1)
        startDateAndTime.set(Calendar.MINUTE, 0)
        _dateStart.value = startDateAndTime
        val endDateAndTime = startDateAndTime.clone() as Calendar
        endDateAndTime.add(Calendar.HOUR_OF_DAY, 1)
        _dateEnd.value = endDateAndTime
    }

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
        _nameValue.value = value
    }

    fun setDescriptionValue(value: String) {
        _descriptionValue.value = value
    }

    fun setStartDate(value: Calendar) {
        val newDate = _dateStart.value!!.clone() as Calendar
        newDate.set(Calendar.DAY_OF_MONTH, value[Calendar.DAY_OF_MONTH])
        newDate.set(Calendar.MONTH, value[Calendar.MONTH])
        newDate.set(Calendar.YEAR, value[Calendar.YEAR])
        _dateStart.value = newDate
    }

    fun setEndDate(value: Calendar) {
        val newDate = _dateEnd.value!!.clone() as Calendar
        newDate.set(Calendar.DAY_OF_MONTH, value[Calendar.DAY_OF_MONTH])
        newDate.set(Calendar.MONTH, value[Calendar.MONTH])
        newDate.set(Calendar.YEAR, value[Calendar.YEAR])
        _dateEnd.value = newDate
    }

    fun setTimeStart(hour: Int, minutes: Int) {
        val newTime = _dateStart.value!!.clone() as Calendar
        newTime.set(Calendar.HOUR_OF_DAY, hour)
        newTime.set(Calendar.MINUTE, minutes)
        _dateStart.value = newTime
    }

    fun setTimeEnd(hour: Int, minutes: Int) {
        val newTime = _dateEnd.value!!.clone() as Calendar
        newTime.set(Calendar.HOUR_OF_DAY, hour)
        newTime.set(Calendar.MINUTE, minutes)
        _dateEnd.value = newTime
    }

    private fun isDateValid(): Boolean {
        val today = Calendar.getInstance()
        val startDate = _dateStart.value?.clone() as Calendar
        val endDate = _dateEnd.value?.clone() as Calendar

        return !startDate.before(today) && endDate.after(startDate)
    }

    private fun areCheckboxesValid(): Boolean {
        return _selectedTechnologyGroups.size >= 1
    }

    private fun isInputValid(): Boolean {
        return _nameValue.value != ""
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

        val name = _nameValue.value!!
        val description = _descriptionValue.value!!
        val timeStart = "${formatTime(_dateStart.value!![Calendar.HOUR_OF_DAY], _dateStart.value!![Calendar.MINUTE])}:00"
        val timeEnd = "${formatTime(_dateEnd.value!![Calendar.HOUR_OF_DAY], _dateEnd.value!![Calendar.MINUTE])}:00"
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
                    eventLogger.log("Dodano wydarzenie: $name")
                    showSnackbar(EventChannel.AddEventSuccess)
                    refreshEventsList()
                    popBackStack()
                } else {
                    eventLogger.log("Błąd dodawania wydarzenia")
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
        val timeStart = timeToString(
            _dateStart.value!![Calendar.HOUR_OF_DAY].toString(),
            _dateStart.value!![Calendar.MINUTE].toString()
        )
        val timeEnd = timeToString(
            _dateStart.value!![Calendar.HOUR_OF_DAY].toString(),
            _dateStart.value!![Calendar.MINUTE].toString()
        )
        val name = _nameValue.value!!

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