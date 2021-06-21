package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.EventScreenChannel
import com.intive.repository.Repository
import com.intive.repository.database.EventLogger
import com.intive.repository.domain.ListUserJava
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider,
    private val eventLogger: EventLogger
) : ViewModel() {

    private val _showDeleteDialog = MutableLiveData(false)
    val showDeleteDialog: LiveData<Boolean> = _showDeleteDialog

    private val eventScreenChannel = Channel<EventScreenChannel>()
    val eventScreenFlow = eventScreenChannel.receiveAsFlow()

    private val _usersList = MutableLiveData<List<ListUserJava>>()
    val usersList: LiveData<List<ListUserJava>> = _usersList

    private fun showSnackbar(message: EventScreenChannel) = viewModelScope.launch {
        eventScreenChannel.send(message)
    }

    fun showDeleteDialog(value: Boolean) {
        _showDeleteDialog.value = value
    }

    private val handler = CoroutineExceptionHandler { _, e -> e.printStackTrace() }

    init {
        getEventUsers()
    }

    private fun getEventUsers() {

        var users: List<ListUserJava>

        viewModelScope.launch(dispatchers.io + handler) {
            users = repository.getEventUsers()
            _usersList.postValue(users)
        }
    }

    fun deleteEvent(eventId: String, popBackStack: () -> Unit, refreshEventsList: () -> Unit) {
        var response: Response<String>

        val handler = CoroutineExceptionHandler { _, _ ->
            showSnackbar(EventScreenChannel.EventDeleteError)
        }

        viewModelScope.launch(handler) {

            withContext(dispatchers.io) {
                response = repository.deleteEvent(eventId)
            }

            if (response.isSuccessful) {
                eventLogger.log("Usunięcie wydarzenia")
                showSnackbar(EventScreenChannel.EventDeleteSuccess)
                refreshEventsList()
                popBackStack()
            } else {
                eventLogger.log("Błąd usunięcia wydarzenia")
                showSnackbar(EventScreenChannel.EventDeleteError)
            }
        }
    }
}