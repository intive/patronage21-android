package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.ListUserJava
import com.intive.repository.domain.model.GroupEntity
import com.intive.repository.network.ROLE_CANDIDATE
import com.intive.repository.network.ROLE_LEADER
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

private const val SEARCH_DEBOUNCE_TIMEOUT = 300L
const val STATUS_ACTIVE = "ACTIVE"

class UsersViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    private val executeQuery: MutableStateFlow<String> = MutableStateFlow("")

    private val _totalLeaders: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalLeaders: State<Resource<Int>> = _totalLeaders

    private val _totalCandidates: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalCandidates: State<Resource<Int>> = _totalCandidates

    private val _techGroups: MutableState<Resource<List<GroupEntity>>> =
        mutableStateOf(Resource.Loading())
    val techGroups: State<Resource<List<GroupEntity>>> = _techGroups

    private val selectedGroup: MutableStateFlow<String?> = MutableStateFlow(null)

    @FlowPreview
    @ExperimentalCoroutinesApi
    var leaders: Flow<Resource<List<ListUserJava>>> = combine(
        executeQuery,
        selectedGroup
    ) { query, selectedGroup ->
        Pair(query, selectedGroup)
    }
        .debounce(SEARCH_DEBOUNCE_TIMEOUT)
        .distinctUntilChanged()
        .flatMapLatest { (query, selectedGroup) ->
            flow <Resource<List<ListUserJava>>> {
                emit(Resource.Loading())
                try {
                    val users = repository.getUsersJava(ROLE_LEADER, selectedGroup, query).users
                    _totalLeaders.value =
                        Resource.Success(users.size)

                    emit(Resource.Success(users))
                } catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage))
                }
            }
        }


    @FlowPreview
    @ExperimentalCoroutinesApi
    var candidates: Flow<Resource<List<ListUserJava>>> = combine(
        executeQuery,
        selectedGroup
    ) { query, selectedGroup ->
        Pair(query, selectedGroup)
    }
        .debounce(SEARCH_DEBOUNCE_TIMEOUT)
        .distinctUntilChanged()
        .flatMapLatest { (query, selectedGroup) ->
            flow <Resource<List<ListUserJava>>> {
                emit(Resource.Loading())
                try {
                    val users = repository.getUsersJava(ROLE_CANDIDATE, selectedGroup, query).users
                    _totalCandidates.value =
                        Resource.Success(users.size)

                    emit(Resource.Success(users))
                }  catch (e: Exception) {
                    emit(Resource.Error(e.localizedMessage))
                }
            }
        }

    init {
        getTechGroups()
    }

    fun onTechGroupsRetryClicked() = viewModelScope.launch(dispatchers.io) {
        getTechGroups()
    }

    fun onTechGroupsChanged(group: String?) {
        viewModelScope.launch {
            selectedGroup.emit(group?.toLowerCase(Locale.ROOT))
        }
    }

    fun onQueryChanged(value: String) {
        val valueTrimmed = value.trim()
        _query.value = value
        viewModelScope.launch(dispatchers.io) {
            executeQuery.emit(valueTrimmed)
        }
    }

    private fun getTechGroups() {

        _techGroups.value = Resource.Loading()

        viewModelScope.launch {
            _techGroups.value = withContext(dispatchers.io) {
                try {
                    val response = repository.getTechnologies().map { group ->
                        GroupEntity(group, group)
                    }
                    Resource.Success(response)
                } catch (e: Exception) {
                    Resource.Error(e.localizedMessage)
                }
            }
        }
    }

}
