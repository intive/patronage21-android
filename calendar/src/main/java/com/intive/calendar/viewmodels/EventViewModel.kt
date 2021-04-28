package com.intive.calendar.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.intive.calendar.R
import com.intive.calendar.utils.isOnline
import com.intive.repository.Repository
import com.intive.repository.domain.model.EventInviteResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(private val repository: Repository) : ViewModel() {

    fun updateInviteResponse(
        userId: Long,
        eventId: Long,
        inviteResponse: String,
        refreshCalendar: () -> Unit,
        view: View,
        context: Context
    ) {

        val response = EventInviteResponse(userId, eventId, inviteResponse)
        val handler = CoroutineExceptionHandler { _, e -> e.printStackTrace() }

        if (isOnline(context)) {
            viewModelScope.launch(handler) {
                withContext(Dispatchers.IO) {
                    repository.updateInviteResponse(response)
                }
                refreshCalendar()
            }
        } else {
            view.let {
                Snackbar.make(
                    it,
                    context.getString(R.string.event_invite_response_error_msg),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}