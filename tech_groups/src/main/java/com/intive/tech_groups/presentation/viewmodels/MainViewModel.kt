package com.intive.tech_groups.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.repository.domain.model.Group
import java.util.logging.Filter

class MainViewModel : ViewModel() {

    val filters = listOf("Wszystkie grupy technologiczne", "Java", "Android", "QA", "JavaScript")
    val groups = listOf(
        Group(name = "Grupa I", description = "des", techList = listOf("Java", "Android")),
        Group(name = "Grupa II", description = "des", techList = listOf("JavaScript", "Android")),
        Group(name = "Grupa III", description = "des", techList = listOf("JavaScript", "Java")),
        Group(name = "Grupa IV", description = "des", techList = listOf("QA", "Android")),
        Group(name = "Grupa V", description = "des", techList = listOf("JavaScript", "QA")),
        Group(name = "Grupa VI", description = "des", techList = listOf("Java", "QA"))

    )

    private val _filteredList = MutableLiveData<List<Group>>(emptyList())
    val filteredList: LiveData<List<Group>> = _filteredList

    init {
        _filteredList.value = groups
    }

    fun filterList(filter: String) {
        val list = mutableListOf<Group>()
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