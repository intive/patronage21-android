package com.intive.gradebook.gradebook

import androidx.lifecycle.ViewModel
import com.intive.gradebook.domain.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GradebookViewModel : ViewModel() {
    val users = List(30) {
        Person(
            firstName = "Jan",
            lastName = "Kowalski"
        )
    }
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}