package com.intive.tech_groups.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.GroupParcelable
import com.intive.repository.util.DispatcherProvider
import com.intive.repository.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

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
            _technologies.value = try {
                val response = repository.getTechnologies()
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage)
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

    private val _responseState: MutableState<Resource<String>?> = mutableStateOf(null)
    val responseState: State<Resource<String>?> = _responseState

    fun addGroup() {
        viewModelScope.launch(dispatchers.io) {
//            if(name.value.isNullOrEmpty()) {
//                _responseState.value = Resource.Error("")
//                return@launch
//            }
            _responseState.value = Resource.Loading()
            val group = GroupParcelable(
                id = "",
                name = name.value!!,
                description = "",
                technologies = chosenTechnologies
            )
            val receivedResponse : Response<String>
            try {
                receivedResponse = repository.addGroup(group)
                if(receivedResponse.isSuccessful) {
                    _responseState.value = Resource.Success("")
                }
                else {
                    _responseState.value = Resource.Error(receivedResponse.message())
                }
            } catch (ex: Exception) {
                _responseState.value = Resource.Error(ex.localizedMessage)
            }
        }
    }
}