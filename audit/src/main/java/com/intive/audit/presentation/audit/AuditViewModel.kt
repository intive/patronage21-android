package com.intive.audit.presentation.audit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuditViewModel : ViewModel() {

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