package com.intive.audit.presentation.audit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.Audit
import com.intive.repository.network.*
import com.intive.repository.util.DispatcherProvider
import com.intive.shared.SortTypes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "AuditViewModel"
const val PAGE_SIZE = 30

class AuditViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    private val executeQuery: MutableStateFlow<String> = MutableStateFlow("")

    private val _sortBy: MutableState<SortTypes> = mutableStateOf(SortTypes.DESC)
    val sortBy: State<SortTypes> = _sortBy

    var executeSortBy: MutableStateFlow<SortTypes> = MutableStateFlow(SortTypes.DESC)

    @FlowPreview
    @ExperimentalCoroutinesApi
    var audits: Flow<PagingData<Audit>> = combine(
        executeQuery,
        executeSortBy
    ) { query, sortBy ->
        Pair(query, sortBy)
    }
        .distinctUntilChanged()
        .flatMapLatest { (query, sortBy) ->
            Pager(PagingConfig(pageSize = PAGE_SIZE)) {
                AuditsSource(repository, query = query, sortBy = sortBy.name.toLowerCase(Locale.ROOT))
            }.flow
                .cachedIn(viewModelScope)
        }

    val showSearchField = mutableStateOf(false)

    val showFilterField = mutableStateOf(false)

    fun onQueryChanged(query: String) {
        _query.value = query
        viewModelScope.launch(dispatchers.io) {
            executeQuery.emit(query)
        }
    }

    fun onSearchIconClick(showSearchField: Boolean){
        this.showSearchField.value = showSearchField
    }

    fun onFilterIconClick(showFilterField: Boolean){
        this.showFilterField.value = showFilterField
    }

    fun onSortByChanged(sort: String) {
        if (sort == "Od najnowszych")
            _sortBy.value = SortTypes.DESC
        else
            _sortBy.value = SortTypes.ASC
        viewModelScope.launch {
            executeSortBy.emit(sortBy.value)
        }
    }
}