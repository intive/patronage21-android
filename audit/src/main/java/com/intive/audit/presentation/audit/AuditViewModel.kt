package com.intive.audit.presentation.audit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.audit.audit_screen.Audit

class AuditViewModel : ViewModel() {

    private val _auditsLiveData = MutableLiveData<List<Audit>>()
    val auditsLiveData: LiveData<List<Audit>>
        get() = _auditsLiveData

    val query = mutableStateOf("")

    val showSearchField = mutableStateOf(false)

    val showFilterField = mutableStateOf(false)

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSearchIconClick(showSearchField: Boolean){
        this.showSearchField.value = showSearchField
    }

    fun onFilterIconClick(showFilterField: Boolean){
        this.showFilterField.value = showFilterField
    }
}