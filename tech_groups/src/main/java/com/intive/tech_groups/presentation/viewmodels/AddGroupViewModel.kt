package com.intive.tech_groups.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddGroupViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun onNameChange(newValue: String) {
        _name.value = newValue
    }

    private val _technologies: MutableState<Resource<List<String>>> =
        mutableStateOf(Resource.Loading())
    val technologies: State<Resource<List<String>>> = _technologies

    init {
        viewModelScope.launch {
            getTechnologies()
        }
    }

    private val chosenTechnologies = mutableListOf<String>()

    private fun getTechnologies() {
        viewModelScope.launch {
            _technologies.value = withContext(dispatchers.io) {
                try {
                    val response = repository.getTechnologies()
                    Resource.Success(response)
                } catch (e: Exception) {
                    Resource.Error(e.localizedMessage)
                }
            }
        }
    }

    fun updateTechnologies(technology: String) {
        if (chosenTechnologies.contains(technology)) {
            chosenTechnologies.remove(technology)
        } else {
            chosenTechnologies.add(technology)
        }
    }
}