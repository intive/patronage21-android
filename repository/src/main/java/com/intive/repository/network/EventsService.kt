package com.intive.repository.network

import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.EventInviteResponseDto
import com.intive.repository.network.model.NewEventDto
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
    @Headers( "Content-Type: application/json" )
    suspend fun updateInviteResponse(@Body event: EventInviteResponseDto): Response<String>

    @POST("api/events")
    @Headers( "Content-Type: application/json" )
    suspend fun addNewEvent(@Body event: NewEventDto): Response<String>

}
