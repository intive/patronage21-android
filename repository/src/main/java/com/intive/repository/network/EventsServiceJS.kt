package com.intive.repository.network

import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.NewEventDto
import retrofit2.Response
import retrofit2.http.*

interface EventsServiceJS {

    @GET("api/events/list/{dateStart}/{dateEnd}")
    suspend fun getEvents(
        @Path("dateStart") dateStart: String,
        @Path("dateEnd") dateEnd: String
    ): List<EventDto>

    @POST("api/events")
    @Headers("Content-Type: application/json")
    suspend fun addNewEvent(@Body event: NewEventDto): Response<Any>
}