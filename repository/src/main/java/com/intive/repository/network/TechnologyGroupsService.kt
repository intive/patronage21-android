package com.intive.repository.network

import com.google.gson.JsonObject
import com.intive.repository.network.model.Group
import retrofit2.Response
import retrofit2.http.*

interface TechnologyGroupsService {
    @GET("api/groups/technologies")
    suspend fun getTechnologies(): List<String>

    @GET("api/groups")
    suspend fun getTechnologyGroups(): List<Group>

    @POST("/api/groups/add")
    suspend fun addGroup(
        @Body group: JsonObject
    ): Response<String>
}