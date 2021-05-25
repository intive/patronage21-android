package com.intive.tech_groups.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.Stage
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch

const val TAG = "GroupDetailsViewModel"

class GroupDetailsViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {

    private val _stages: MutableState<Resource<List<Stage>>> = mutableStateOf(Resource.Loading())
    val stages: State<Resource<List<Stage>>> = _stages

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

    fun onGetStagesRetryClick(groupId: String){
        getStages(groupId)
    }

}