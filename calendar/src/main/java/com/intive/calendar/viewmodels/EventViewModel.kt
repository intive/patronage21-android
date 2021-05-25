package com.intive.calendar.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.calendar.utils.InviteResponseChannel
import com.intive.repository.Repository
import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.*

class EventViewModel(
    private val repository: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _showDeleteDialog = MutableLiveData(false)
    val showDeleteDialog: LiveData<Boolean> = _showDeleteDialog

    private val inviteResponseChannel = Channel<InviteResponseChannel>()
    val inviteResponseFlow = inviteResponseChannel.receiveAsFlow()

    private fun showSnackbar() = viewModelScope.launch {
        inviteResponseChannel.send(InviteResponseChannel.Error)
    }

    fun showDeleteDialog(value: Boolean) {
        _showDeleteDialog.value = value
    }

    fun updateInviteResponse(
        userId: Long,
        eventId: Long,
        inviteResponse: String,
        refreshCalendar: () -> Unit
    ) {


        val res = EventInviteResponse(userId, eventId, inviteResponse)

        val handler = CoroutineExceptionHandler { _, _ ->
            showSnackbar()
        }


        var response: Response<String>

        viewModelScope.launch(handler) {

            withContext(dispatchers.io) {
                response = repository.updateInviteResponse(res)
            }

            if (response.isSuccessful) {
                refreshCalendar()
            } else {
                showSnackbar()
            }
        }

    }
}