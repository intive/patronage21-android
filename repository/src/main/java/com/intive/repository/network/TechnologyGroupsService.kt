package com.intive.repository.network

import com.google.gson.JsonObject
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import retrofit2.http.*

interface TechnologyGroupsService {
    @GET("api/groups")
    suspend fun getTechGroups(): List<String>

    @POST("api/register")
    suspend fun sendDataFromRegistrationForm(
        @Body user: UserRegistration
    ) : Response<String>

    @POST("api/registration/verify")
    suspend fun sendCodeToServer(
        @Body body: JsonObject
    ) : Response<String>
}