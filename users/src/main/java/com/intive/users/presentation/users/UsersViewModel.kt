package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.network.ROLE_CANDIDATE
import com.intive.repository.network.ROLE_LEADER
import com.intive.repository.network.USERS_PAGE_SIZE
import com.intive.repository.network.UsersSource
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    private val _totalLeaders: MutableState<Int> = mutableStateOf(0)
    val totalLeaders: State<Int> = _totalLeaders

    private val _totalCandidates: MutableState<Int> = mutableStateOf(0)
    val totalCandidates: State<Int> = _totalCandidates

    var leaders: Flow<PagingData<User>> = Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(repository, ROLE_LEADER)
    }.flow
        .cachedIn(viewModelScope)

    var candidates: Flow<PagingData<User>> = Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(repository, ROLE_CANDIDATE)
    }.flow
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch(dispatchers.io) {
            _totalCandidates.value = repository.getTotalUsersByRole(ROLE_CANDIDATE)
            _totalLeaders.value = repository.getTotalUsersByRole(ROLE_LEADER)
        }
    }

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}