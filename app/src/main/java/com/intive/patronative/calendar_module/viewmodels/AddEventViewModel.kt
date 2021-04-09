package com.intive.patronative.calendar_module.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class AddEventViewModel : ViewModel() {
    private val c: Calendar = Calendar.getInstance()
    private val hour = c[Calendar.HOUR_OF_DAY]

    private val _date = MutableLiveData(Calendar.getInstance())
    val date: LiveData<Calendar> = _date

    private val _hourStart = MutableLiveData(hour + 1)
    var hourStart: LiveData<Int> = _hourStart

    private val _hourEnd = MutableLiveData(hour + 2)
    var hourEnd: LiveData<Int> = _hourEnd

    private val _minutesStart = MutableLiveData(0)
    var minutesStart: LiveData<Int> = _minutesStart

    private val _minutesEnd = MutableLiveData(0)
    var minutesEnd: LiveData<Int> = _minutesEnd

    private val _checkbox1 = MutableLiveData(false)
    var checkbox1: LiveData<Boolean> = _checkbox1

    private val _checkbox2 = MutableLiveData(false)
    var checkbox2: LiveData<Boolean> = _checkbox2

    private val _checkbox3 = MutableLiveData(false)
    var checkbox3: LiveData<Boolean> = _checkbox3

    private val _checkbox4 = MutableLiveData(false)
    var checkbox4: LiveData<Boolean> = _checkbox4

    private val _inputValue = MutableLiveData("")
    var inputValue: LiveData<String> = _inputValue

    fun setInputValue(value: String) {
        _inputValue.value = value
    }

    fun setDate(value: Calendar) {
        _date.value = value
    }

    fun setTimeStart(hour: Int, minutes: Int) {
        _hourStart.value = hour
        _minutesStart.value = minutes
    }

    fun setTimeEnd(hour: Int, minutes: Int) {
        _hourEnd.value = hour
        _minutesEnd.value = minutes
    }

    fun setCheckbox1() {
        _checkbox1.value = _checkbox1.value != true
    }

    fun setCheckbox2() {
        _checkbox2.value = _checkbox2.value != true
    }

    fun setCheckbox3() {
        _checkbox3.value = _checkbox3.value != true
    }

    fun setCheckbox4() {
        _checkbox4.value = _checkbox4.value != true
    }

    fun validateDate(): Boolean {
        val today: Calendar = Calendar.getInstance()
        _hourStart.value?.let { _date.value?.set(Calendar.HOUR, it) }
        _minutesStart.value?.let { _date.value?.set(Calendar.MINUTE, it) }

        return today.before(_date.value)

    }

    fun validateTime(): Boolean {
        val endDate = _date.value?.clone() as Calendar
        _hourEnd.value?.let { endDate.set(Calendar.HOUR, it) }
        _minutesEnd.value?.let { endDate.set(Calendar.MINUTE, it) }

        return !endDate.before(_date.value)
    }

    fun validateCheckboxes(): Boolean {
        return _checkbox1.value == true || _checkbox2.value == true || _checkbox3.value == true || _checkbox4.value == true
    }

    fun validateInput(): Boolean {
        return _inputValue.value != ""
    }


}