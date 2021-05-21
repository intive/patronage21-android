package com.intive.tech_groups.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.repository.domain.model.GroupParcelable

class MainViewModel : ViewModel() {

    val filters = listOf("Wszystkie grupy technologiczne", "Java", "Android", "QA", "JavaScript")
    val groups = listOf(
        GroupParcelable(id = "0", name = "Grupa I", description = "des", techList = listOf("Java", "Android")),
        GroupParcelable(id = "1", name = "Grupa II", description = "des", techList = listOf("JavaScript", "Android")),
        GroupParcelable(id = "2", name = "Grupa III", description = "des", techList = listOf("JavaScript", "Java")),
        GroupParcelable(id = "3", name = "Grupa IV", description = "des", techList = listOf("QA", "Android")),
        GroupParcelable(id = "4", name = "Grupa V", description = "des", techList = listOf("JavaScript", "QA")),
        GroupParcelable(id = "5", name = "Grupa VI", description = "des", techList = listOf("Java", "QA"))

    )

    private val _filteredList = MutableLiveData<List<GroupParcelable>>(emptyList())
    val filteredList: LiveData<List<GroupParcelable>> = _filteredList

    init {
        _filteredList.value = groups
    }

    fun filterList(filter: String) {
        val list = mutableListOf<GroupParcelable>()
        if (filter == "Wszystkie grupy technologiczne") {
            list.addAll(groups)
        }
        else {
            for (item in groups) {
                if (item.techList.contains(filter)) {
                    list.add(item)
                }
            }
        }
        _filteredList.value = list
    }
}