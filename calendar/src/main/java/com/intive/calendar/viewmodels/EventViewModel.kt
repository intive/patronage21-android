package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.EventScreenChannel
import com.intive.repository.Repository
import com.intive.repository.database.EventLogger
import com.intive.repository.domain.model.EventInviteResponse
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

    private fun showSnackbar(message: EventScreenChannel) = viewModelScope.launch {
        eventScreenChannel.send(message)
    }

    fun showDeleteDialog(value: Boolean) {
        _showDeleteDialog.value = value
    }

    fun updateInviteResponse(
        userId: Long,
        eventId: Long,
        inviteResponse: String,
        refreshEventsList: () -> Unit
    ) {

        val res = EventInviteResponse(userId, eventId, inviteResponse)

        val handler = CoroutineExceptionHandler { _, _ ->
            showSnackbar(EventScreenChannel.InviteResponseError)
        }

        var response: Response<String>

        viewModelScope.launch(handler) {

            withContext(dispatchers.io) {
                response = repository.updateInviteResponse(res)
            }

            if (response.isSuccessful) {
                eventLogger.log("Pomyślna edycja wydarzenia")
                refreshEventsList()
            } else {
                eventLogger.log("Błąd edycji wydarzenia")
                showSnackbar(EventScreenChannel.InviteResponseError)
            }
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