package com.intive.patronative.audit_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuditScreenViewModel : ViewModel() {

    private val _auditsLiveData = MutableLiveData<List<Audit>>()
    val auditsLiveData: LiveData<List<Audit>>
        get() = _auditsLiveData


}