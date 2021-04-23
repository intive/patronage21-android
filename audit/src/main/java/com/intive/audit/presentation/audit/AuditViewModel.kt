package com.intive.audit.presentation.audit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.intive.repository.Repository

class AuditViewModel(
    private val repository: Repository
) : ViewModel() {

    val query = mutableStateOf("")

    val showSearchField = mutableStateOf(false)

    val showFilterField = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    fun onTriggerEvent(event: AuditEvent){

    }

    private suspend fun newSearch(){

        //val result =
    }

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