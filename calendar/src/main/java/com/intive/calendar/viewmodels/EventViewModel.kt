package com.intive.calendar.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.intive.calendar.R
import com.intive.repository.Repository
import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventViewModel(private val repository: Repository, private val dispatchers: DispatcherProvider) : ViewModel() {

    fun updateInviteResponse(
        userId: Long,
        eventId: Long,
        inviteResponse: String,
        refreshCalendar: () -> Unit,
        view: View,
        context: Context
    ) {


        val res = EventInviteResponse(userId, eventId, inviteResponse)

        val handler = CoroutineExceptionHandler { _, _ ->
            view.let {
                Snackbar.make(
                    it,
                    context.getString(R.string.event_invite_response_error_msg),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }


        var response: Response<String>

        viewModelScope.launch(handler) {

            withContext(dispatchers.io) {
                response = repository.updateInviteResponse(res)
            }

            if (response.isSuccessful) {
                refreshCalendar()
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
}