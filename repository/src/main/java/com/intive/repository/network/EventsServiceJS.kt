package com.intive.repository.network

import com.intive.repository.network.model.NewEventDto
import retrofit2.Response
import retrofit2.http.*

interface EventsServiceJS {
    @POST("api/events")
    @Headers("Content-Type: application/json")
    suspend fun addNewEvent(@Body event: NewEventDto): Response<Any>
}