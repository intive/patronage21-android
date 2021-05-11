package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.network.ROLE_CANDIDATE
import com.intive.repository.network.ROLE_LEADER
import com.intive.repository.network.USERS_PAGE_SIZE
import com.intive.repository.network.UsersSource
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

private const val ALL_GROUPS = "wszystkie grupy"
private const val SEARCH_DEBOUNCE_TIMEOUT = 300L

class UsersViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    private val executeQuery: MutableStateFlow<String> = MutableStateFlow("")

    private val _totalLeaders: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalLeaders: State<Resource<Int>> = _totalLeaders

    private val _totalCandidates: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalCandidates: State<Resource<Int>> = _totalCandidates

    private val _techGroups: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val techGroups: State<Resource<List<String>>> = _techGroups

    private val selectedGroup: MutableStateFlow<String?> = MutableStateFlow(null)

    @FlowPreview
    @ExperimentalCoroutinesApi
    var leaders: Flow<PagingData<User>> = combine(
        executeQuery,
        selectedGroup
    ) { query, selectedGroup ->
        Pair(query, selectedGroup)
    }
        .debounce(SEARCH_DEBOUNCE_TIMEOUT)
        .distinctUntilChanged()
        .flatMapLatest { (query, selectedGroup) ->
        val group = if (selectedGroup == ALL_GROUPS) null else selectedGroup

        Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
            UsersSource(repository, ROLE_LEADER, group = group, query = query)
        }.flow
            .cachedIn(viewModelScope)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    var candidates: Flow<PagingData<User>> = combine(
        executeQuery,
        selectedGroup
    ) { query, selectedGroup ->
        Pair(query, selectedGroup)
    }
        .debounce(SEARCH_DEBOUNCE_TIMEOUT)
        .distinctUntilChanged()
        .flatMapLatest { (query, selectedGroup) ->
        val group = if (selectedGroup == ALL_GROUPS) null else selectedGroup

        Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
            UsersSource(repository, ROLE_CANDIDATE, group = group, query = query)
        }.flow
            .cachedIn(viewModelScope)
    }

    init {
        viewModelScope.launch(dispatchers.io) {
            _techGroups.value = try {
                val response = repository.getTechnologies()
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }

        getTotalCandidatesCount(group = null, query = executeQuery.value)
        getTotalLeadersCount(group = null, query = executeQuery.value)
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

    fun onTechGroupsChanged(group: String) {
        viewModelScope.launch {
            selectedGroup.emit(group.toLowerCase(Locale.ROOT))
        }

        if (selectedGroup.value == ALL_GROUPS) {
            getTotalCandidatesCount(group = null, query = executeQuery.value)
            getTotalLeadersCount(group = null, query = executeQuery.value)
        } else {
            getTotalCandidatesCount(group = selectedGroup.value, query = executeQuery.value)
            getTotalLeadersCount(group = selectedGroup.value, query = executeQuery.value)
        }

    }

    fun onQueryChanged(value: String) {
        val valueTrimmed = value.trim()
        _query.value = value
        viewModelScope.launch(dispatchers.io) {
            executeQuery.emit(valueTrimmed)

            val group = if (selectedGroup.value == ALL_GROUPS) null else selectedGroup.value
            getTotalCandidatesCount(group = group, query = valueTrimmed)
            getTotalLeadersCount(group = group, query = valueTrimmed)
        }
    }

    fun onCandidatesRetryClicked() {
        getTotalCandidatesCount(group = selectedGroup.value, query = executeQuery.value)
    }

    fun onLeadersRetryClicked() {
        getTotalLeadersCount(group = selectedGroup.value, query = executeQuery.value)
    }

    private fun getTotalCandidatesCount(group: String?, query: String) =
        viewModelScope.launch(dispatchers.io) {
            _totalCandidates.value = try {
                val response: Int = repository.getUsers(1, ROLE_CANDIDATE, group, query).totalSize
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }

    private fun getTotalLeadersCount(group: String?, query: String) =
        viewModelScope.launch(dispatchers.io) {
            _totalLeaders.value = try {
                val response: Int = repository.getUsers(1, ROLE_LEADER, group, query).totalSize
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }

}
