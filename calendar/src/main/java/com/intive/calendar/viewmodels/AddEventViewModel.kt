package com.intive.calendar.viewmodels

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

    private val _checkboxJS = MutableLiveData(false)
    var checkboxJS: LiveData<Boolean> = _checkboxJS

    private val _checkboxJava = MutableLiveData(false)
    var checkboxJava: LiveData<Boolean> = _checkboxJava

    private val _checkboxQA = MutableLiveData(false)
    var checkboxQA: LiveData<Boolean> = _checkboxQA

    private val _checkboxMobile = MutableLiveData(false)
    var checkboxMobile: LiveData<Boolean> = _checkboxMobile

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

    fun setCheckboxJS() {
        _checkboxJS.value = _checkboxJS.value != true
    }

    fun setCheckboxJava() {
        _checkboxJava.value = _checkboxJava.value != true
    }

    fun setCheckboxQA() {
        _checkboxQA.value = _checkboxQA.value != true
    }

    fun setCheckboxMobile() {
        _checkboxMobile.value = _checkboxMobile.value != true
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
        return _checkboxJS.value == true || _checkboxJava.value == true || _checkboxQA.value == true || _checkboxMobile.value == true
    }

    fun validateInput(): Boolean {
        return _inputValue.value != ""
    }

}