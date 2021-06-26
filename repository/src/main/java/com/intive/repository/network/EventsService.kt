package com.intive.repository.network

import com.intive.repository.domain.ListUserJava
import com.intive.repository.network.model.EditEventDto
import com.intive.repository.network.model.EventInviteResponseDto
import com.intive.repository.network.model.UserDto
import retrofit2.Response

import retrofit2.http.*

interface EventsService {

    @PUT("api/events/update")
    @Headers("Content-Type: application/json")
    suspend fun updateInviteResponse(@Body event: EventInviteResponseDto): Response<String>

    @GET("api/events/users")
    suspend fun getEventUsers(): List<ListUserJava>

}
