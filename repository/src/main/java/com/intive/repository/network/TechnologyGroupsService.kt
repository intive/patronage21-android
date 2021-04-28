package com.intive.repository.network

import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TechnologyGroupsService {
    @GET("api/groups")
    suspend fun getTechGroups(): List<String>

    @POST("api/register")
    suspend fun sendDataFromRegistrationForm(
        @Body user: UserRegistration
    ) : Response<String>
}