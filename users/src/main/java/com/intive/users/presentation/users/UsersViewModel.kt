package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.network.ROLE_CANDIDATE
import com.intive.repository.network.ROLE_LEADER
import com.intive.repository.network.USERS_PAGE_SIZE
import com.intive.repository.network.UsersSource
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    private val _totalLeaders: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalLeaders: State<Resource<Int>> = _totalLeaders

    private val _totalCandidates: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalCandidates: State<Resource<Int>> = _totalCandidates

    private val _techGroups: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val techGroups: State<Resource<List<String>>> = _techGroups

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
            _totalCandidates.value = try {
                val response = repository.getTotalUsersByRole(ROLE_CANDIDATE)
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
            _totalLeaders.value = try {
                val response = repository.getTotalUsersByRole(ROLE_LEADER)
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }

            _techGroups.value = try {
                val response = repository.getTechnologies()
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }

    fun onTechGroupsRetryClicked() = viewModelScope.launch(dispatchers.io) {
        _techGroups.value = Resource.Loading()
        _techGroups.value = try {
            val response = repository.getTechnologies()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}
