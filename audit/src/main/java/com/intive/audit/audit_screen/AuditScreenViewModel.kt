package com.intive.audit.audit_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.audit.audit_screen.Audit

class AuditScreenViewModel : ViewModel() {

    private val _auditsLiveData = MutableLiveData<List<Audit>>()
    val auditsLiveData: LiveData<List<Audit>>
        get() = _auditsLiveData


}