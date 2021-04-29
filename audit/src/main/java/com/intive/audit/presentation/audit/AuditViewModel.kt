package com.intive.audit.presentation.audit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.intive.repository.Repository
import com.intive.repository.network.AuditsSource

const val TAG = "AuditViewModel"
const val PAGE_SIZE = 30

class AuditViewModel(
    private val repository: Repository
) : ViewModel() {

    val query = MutableLiveData("")

    var audits = query.switchMap {
        Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            AuditsSource(repository, it)
        }.liveData
            .cachedIn(viewModelScope)
    }

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