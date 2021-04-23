package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.network.ROLE_CANDIDATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    var candidates: Flow<PagingData<User>> = flowOf()

    init {
         viewModelScope.launch {
           candidates = repository.getUsersByRole(ROLE_CANDIDATE).cachedIn(viewModelScope)
        }
    }

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}