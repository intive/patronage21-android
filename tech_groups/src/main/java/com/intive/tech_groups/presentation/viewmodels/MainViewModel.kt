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
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch
import java.util.logging.Filter

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _filters: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val filters: State<Resource<List<String>>> = _filters
    //val filters = listOf("Wszystkie grupy technologiczne", "Java", "Android", "QA", "JavaScript")

    private val _groups: MutableState<Resource<List<Group>>> =
        mutableStateOf(Resource.Loading())
    val groups: State<Resource<List<Group>>> = _groups
//    val groups = listOf(
//        Group(name = "Grupa I", description = "des", techList = listOf("Java", "Android")),
//        Group(name = "Grupa II", description = "des", techList = listOf("JavaScript", "Android")),
//        Group(name = "Grupa III", description = "des", techList = listOf("JavaScript", "Java")),
//        Group(name = "Grupa IV", description = "des", techList = listOf("QA", "Android")),
//        Group(name = "Grupa V", description = "des", techList = listOf("JavaScript", "QA")),
//        Group(name = "Grupa VI", description = "des", techList = listOf("Java", "QA"))
//
//    )

    private val _filteredList = MutableLiveData<List<Group>>(emptyList())
    val filteredList: LiveData<List<Group>> = _filteredList

    init {
        viewModelScope.launch {
            getFilters()
            getGroups()
        }
    }


    fun filterList(filter: String) {
        val list = mutableListOf<Group>()
        if (filter == "Wszystkie grupy technologiczne") {
            groups.value.data?.let { list.addAll(it) }
        }
        else {
            groups.value.data?.let {
                for(item in it) {
                    if(item.technologies.contains(filter)) {
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
            _filters.value = try {
                val response = repository.getTechnologies()
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
            }
        }
    }
}