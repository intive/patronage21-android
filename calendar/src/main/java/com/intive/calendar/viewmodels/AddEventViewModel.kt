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

    private val _hourStart = MutableLiveData("${hour + 1}")
    var hourStart: LiveData<String> = _hourStart

    private val _hourEnd = MutableLiveData("${hour + 2}")
    var hourEnd: LiveData<String> = _hourEnd

    private val _minutesStart = MutableLiveData("00")
    var minutesStart: LiveData<String> = _minutesStart

    private val _minutesEnd = MutableLiveData("00")
    var minutesEnd: LiveData<String> = _minutesEnd

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

    private fun formatTime(hour: Int, minutes: Int): Pair<String, String> {

        val hourString = when {
            hour < 10 -> "0$hour"
            else -> "$hour"
        }

        val minutesString = when {
            minutes == 0 -> "00"
            minutes < 10 -> "0${minutes}"
            else -> "$minutes"
        }

        return Pair(hourString, minutesString)
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
        _hourStart.value?.let { _date.value?.set(Calendar.HOUR, it.toInt()) }
        _minutesStart.value?.let { _date.value?.set(Calendar.MINUTE, it.toInt()) }
        return today.before(_date.value)
    }

    fun validateTime(): Boolean {
        val endDate = _date.value?.clone() as Calendar
        _hourEnd.value?.let { endDate.set(Calendar.HOUR, it.toInt()) }
        _minutesEnd.value?.let { endDate.set(Calendar.MINUTE, it.toInt()) }
        return !endDate.before(_date.value)
    }

    fun validateCheckboxes(): Boolean {
        return _checkboxJS.value == true || _checkboxJava.value == true || _checkboxQA.value == true || _checkboxMobile.value == true
    }

    fun validateInput(): Boolean {
        return _inputValue.value != ""
    }

}