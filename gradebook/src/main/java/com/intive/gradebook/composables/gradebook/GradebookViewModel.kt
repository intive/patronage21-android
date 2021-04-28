package com.intive.gradebook.composables.gradebook

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.intive.repository.Repository
import com.intive.repository.domain.model.Gradebook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.ViewModelProvider


class GradebookViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    var participants: Flow<PagingData<Gradebook>> = flowOf()

    init {
        viewModelScope.launch {
            participants = repository.getGradebook().cachedIn(viewModelScope)
        }
    }

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}