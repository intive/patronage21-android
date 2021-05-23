package com.intive.gradebook.composables.gradebook

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.intive.repository.Repository
import com.intive.repository.domain.model.Gradebook
import com.intive.repository.network.GRADEBOOK_PAGE_SIZE
import com.intive.repository.network.GradebookSource
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class GradebookViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _techGroups: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val techGroups: State<Resource<List<String>>> = _techGroups

    var groupStorage = "all"
    var sortbyStorage = "alpha"
    private val selectedGroupSort: MutableStateFlow<String> = MutableStateFlow("all:alpha")

    @ExperimentalCoroutinesApi
    var participants: Flow<PagingData<Gradebook>> = selectedGroupSort.flatMapLatest { g ->
        val parts = g.split(':')
        Pager(PagingConfig(pageSize = GRADEBOOK_PAGE_SIZE)) {
            GradebookSource(repository, group = parts[0], sortby = parts[1])
        }.flow
            .cachedIn(viewModelScope)
    }

    init {
        viewModelScope.launch {
            _techGroups.value = withContext(dispatchers.io) {
                try {
                    val response = repository.getTechnologies()
                    Resource.Success(response)
                } catch (e: Exception) {
                    Resource.Error(e.localizedMessage)
                }
            }
        }
    }

    fun onTechGroupsRetryClicked() {

        _techGroups.value = Resource.Loading()

        viewModelScope.launch {
            _techGroups.value = withContext(dispatchers.io) {
                try {
                    val response = repository.getTechnologies()
                    Resource.Success(response)
                } catch (e: Exception) {
                    Resource.Error(e.localizedMessage)
                }
            }
        }
    }

    fun onTechGroupsChanged(group: String) {
        println(group)
        if (group == "Wszystkie grupy")
            groupStorage = "all"
        else if (group == "Mobile (Android)")
            groupStorage = "android"
        else
            groupStorage = group.toLowerCase(Locale.ROOT)
        viewModelScope.launch {
            selectedGroupSort.emit(groupStorage + ":" + sortbyStorage)
        }
    }

    fun onSortByChanged(sort: String) {
        println(sort)
        if (sort == "Alfabetycznie")
            sortbyStorage = "alpha"
        else if (sort == "Od najwyższych ocen")
            sortbyStorage = "desc"
        else
            sortbyStorage = "asc"
        viewModelScope.launch {
            selectedGroupSort.emit(groupStorage + ":" + sortbyStorage)
        }
    }
}