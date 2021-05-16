package com.intive.tech_groups.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.repository.Repository
import com.intive.repository.domain.model.StageDetails
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class StageViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _stageDetails = MutableLiveData<StageDetails?>()
    val stageDetails: LiveData<StageDetails?> = _stageDetails

    fun getStageDetails(id: Long) {
        val handler = CoroutineExceptionHandler { _, e -> e.printStackTrace() }

        var stage: StageDetails

        viewModelScope.launch(dispatchers.io + handler) {

            stage = repository.getStageDetails(id)
            _stageDetails!!.postValue(stage)
        }
    }

}