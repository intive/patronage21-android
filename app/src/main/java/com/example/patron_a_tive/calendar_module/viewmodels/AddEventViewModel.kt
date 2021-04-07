package com.example.patron_a_tive.calendar_module.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class AddEventViewModel() : ViewModel() {
    val c: Calendar = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val hour = c[Calendar.HOUR_OF_DAY]


    private val _date = MutableLiveData("$day.$month.$year")
    val date: LiveData<String> = _date

    private val _timeStart = MutableLiveData("${hour+1}:00")
    var timeStart: LiveData<String> = _timeStart

    private val _timeEnd = MutableLiveData("${hour+2}:00")
    var timeEnd: LiveData<String> = _timeEnd

    private val _checkbox1 = MutableLiveData(false)
    var checkbox1: LiveData<Boolean> = _checkbox1

    fun setCheckbox1() {
        _checkbox1.value = _checkbox1.value != true
    }

    private val _checkbox2 = MutableLiveData(false)
    var checkbox2: LiveData<Boolean> = _checkbox2

    fun setCheckbox2() {
        _checkbox2.value = _checkbox2.value != true
    }

    private val _checkbox3 = MutableLiveData(false)
    var checkbox3: LiveData<Boolean> = _checkbox3

    fun setCheckbox3() {
        _checkbox3.value = _checkbox3.value != true
    }

    private val _checkbox4 = MutableLiveData(false)
    var checkbox4: LiveData<Boolean> = _checkbox4

    fun setCheckbox4() {
        _checkbox4.value = _checkbox4.value != true
    }

    private val _inputValue = MutableLiveData("")
    var inputValue: LiveData<String> = _inputValue

    fun setInputValue(value: String) {
        _inputValue.value = value
    }

    fun setDate(value: String) {
        _date.value = value
    }

    fun setTimeStart(value: String) {
        _timeStart.value = value
    }

    fun setTimeEnd(value: String) {
        _timeEnd.value = value
    }
}