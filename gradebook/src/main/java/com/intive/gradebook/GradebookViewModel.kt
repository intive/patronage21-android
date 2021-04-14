package com.intive.gradebook

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GradebookViewModel : ViewModel() {
    val users = listOf(
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski"),
        Person("Jan", "Kowalski")
    )
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}