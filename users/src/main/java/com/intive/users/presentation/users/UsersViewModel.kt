package com.intive.users.presentation.users

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(
    private val repository: Repository
) : ViewModel() {
    val users = mutableStateOf<List<User>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO){
            try{
                users.value = repository.getUsers()
            } catch (e: Exception) {
                println("EXCEPTION VM: " + e.localizedMessage)
            }
        }
    }

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    fun onQueryChanged(value: String) {
        _query.value = value
    }
}