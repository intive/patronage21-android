package com.intive.repository.network

import com.intive.repository.network.model.EditEventDto
import com.intive.repository.network.model.EventInviteResponseDto
import retrofit2.Response

import retrofit2.http.*

interface EventsService {

    @PUT("api/events/update")
    @Headers("Content-Type: application/json")
    suspend fun updateInviteResponse(@Body event: EventInviteResponseDto): Response<String>

    @DELETE("api/events")
    suspend fun deleteEvent(@Query("id") id: String): Response<String>

    @PUT("api/events/edit")
    @Headers("Content-Type: application/json")
    suspend fun editEvent(@Body event: EditEventDto, @Query("id") id: String): Response<String>

}
