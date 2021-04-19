package com.intive.audit.presentation.audit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.audit.audit_screen.Audit

class AuditViewModel : ViewModel() {

    private val _auditsLiveData = MutableLiveData<List<Audit>>()
    val auditsLiveData: LiveData<List<Audit>>
        get() = _auditsLiveData


}