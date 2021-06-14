package com.intive.tech_groups.presentation.viewmodels

import android.util.Log
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
import com.intive.repository.domain.model.GroupParcelable
import com.intive.repository.domain.model.Stage
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

const val TAG = "GroupDetailsViewModel"

class GroupDetailsViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    private val _stages: MutableState<Resource<List<Stage>>> = mutableStateOf(Resource.Loading())
    val stages: State<Resource<List<Stage>>> = _stages

    private val _totalLeaders: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalLeaders: State<Resource<Int>> = _totalLeaders

    private val _totalCandidates: MutableState<Resource<Int>> = mutableStateOf(Resource.Loading())
    val totalCandidates: State<Resource<Int>> = _totalCandidates

    lateinit var selectedGroup: GroupParcelable

    @FlowPreview
    @ExperimentalCoroutinesApi
    var leaders: Flow<PagingData<User>> =
        Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(repository, ROLE_LEADER, group = selectedGroup.name.filter { !it.isWhitespace() }, query = "")
    }.flow
            .onStart { getTotalLeadersCount(group = selectedGroup.name.filter { !it.isWhitespace() }) }
            .cachedIn(viewModelScope)

    @FlowPreview
    @ExperimentalCoroutinesApi
    var candidates: Flow<PagingData<User>> =
        Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(repository, ROLE_CANDIDATE, group = selectedGroup.name.filter { !it.isWhitespace() }, query = "")
    }.flow
            .onStart { getTotalCandidatesCount(group = selectedGroup.name.filter { !it.isWhitespace() }) }
            .cachedIn(viewModelScope)

    fun getStages(groupId: String){
        viewModelScope.launch(dispatchers.io) {
            _stages.value = try {
                val response: List<Stage> = repository.getStages(groupId)
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }

    private fun getTotalCandidatesCount(group: String?) =
        viewModelScope.launch(dispatchers.io) {
            _totalCandidates.value = try {
                val response: Int = repository.getUsers(1, ROLE_CANDIDATE, group).totalSize
                Log.d(TAG, response.toString())
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }

    private fun getTotalLeadersCount(group: String?) =
        viewModelScope.launch(dispatchers.io) {
            _totalLeaders.value = try {
                val response: Int = repository.getUsers(1, ROLE_LEADER, group).totalSize
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }

    fun onGetStagesRetryClick(groupId: String){
        getStages(groupId)
    }

    fun onCandidatesRetryClicked() {
        getTotalCandidatesCount(group = selectedGroup.name)
    }

    fun onLeadersRetryClicked() {
        getTotalLeadersCount(group = selectedGroup.name)
    }

}