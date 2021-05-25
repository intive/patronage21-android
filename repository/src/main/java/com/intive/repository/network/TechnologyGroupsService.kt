package com.intive.repository.network

import com.intive.repository.domain.model.GroupParcelable
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface TechnologyGroupsService {
    @GET("api/groups/technologies")
    suspend fun getTechnologies(): List<String>

    @GET("api/groups")
    suspend fun getTechnologyGroups(): List<GroupParcelable>

    @POST("/api/groups/add")
    suspend fun addGroup(
        @Body group: JsonObject
    ): Response<String>
}