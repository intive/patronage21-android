package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.Flow

class UsersViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    var leaders: Flow<PagingData<User>> = Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(repository, ROLE_LEADER)
    }.flow
        .cachedIn(viewModelScope)

    var candidates: Flow<PagingData<User>> = Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(repository, ROLE_CANDIDATE)
    }.flow
        .cachedIn(viewModelScope)

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}