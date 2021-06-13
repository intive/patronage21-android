package com.intive.repository.network

import com.intive.repository.network.model.EditEventDto
import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.EventInviteResponseDto
import retrofit2.Response

import retrofit2.http.*

interface EventsService {

    @GET("api/events")
    suspend fun getEvents(
        @Query("dateStart") dateStart: String,
        @Query("dateEnd") dateEnd: String,
        @Query("userId") userId: Long
    ): List<EventDto>

    @PUT("api/events/update")
    @Headers("Content-Type: application/json")
    suspend fun updateInviteResponse(@Body event: EventInviteResponseDto): Response<String>

    @DELETE("api/events")
    suspend fun deleteEvent(@Query("id") id: Long): Response<String>

    @PUT("api/events/edit")
    @Headers("Content-Type: application/json")
    suspend fun editEvent(@Body event: EditEventDto, @Query("id") id: Long): Response<String>

}
