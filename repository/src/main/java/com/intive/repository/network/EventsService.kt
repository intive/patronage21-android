package com.intive.repository.network

import com.intive.repository.network.model.EventDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {

    @GET("api/events")
    suspend fun getEvents(
        @Query("dateStart") dateStart: String,
        @Query("dateEnd") dateEnd: String,
        @Query("userId") userId: Long
    ): List<EventDto>

}
