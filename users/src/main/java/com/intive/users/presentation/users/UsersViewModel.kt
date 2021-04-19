package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.network.CandidatesSource
import kotlinx.coroutines.flow.Flow

class UsersViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }

    fun getCandidatesPagination(): Flow<PagingData<User>> {
        return Pager(PagingConfig(pageSize = 24)) {
            CandidatesSource(repository)
        }.flow
    }
}