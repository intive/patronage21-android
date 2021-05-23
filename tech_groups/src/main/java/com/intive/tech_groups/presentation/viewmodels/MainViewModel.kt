package com.intive.tech_groups.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.Group
import com.intive.repository.domain.model.GroupEntity
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _filters: MutableState<Resource<List<GroupEntity>>> =
        mutableStateOf(Resource.Loading())
    val filters: State<Resource<List<GroupEntity>>> = _filters

    private val _groups: MutableState<Resource<List<Group>>> =
        mutableStateOf(Resource.Loading())
    val groups: State<Resource<List<Group>>> = _groups

    private val _filteredList = MutableLiveData<List<Group>>(emptyList())
    val filteredList: LiveData<List<Group>> = _filteredList

    init {
        viewModelScope.launch {
            getFilters()
            getGroups()
        }
    }


    fun filterList(filter: String?) {
        val list = mutableListOf<Group>()
        if (filter == null) {
            groups.value.data?.let { list.addAll(it) }
        } else {
            groups.value.data?.let {
                for (item in it) {
                    if (item.technologies.contains(filter)) {
                        list.add(item)
                    }
                }
            }
        }
        _filteredList.value = list
    }

    fun getGroups() {
        viewModelScope.launch {
            _groups.value = try {
                val response = repository.getTechnologyGroups()
                _filteredList.value = response
                println(response)
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }

    fun getFilters() {
        viewModelScope.launch {
            _filters.value = withContext(dispatchers.io) {
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