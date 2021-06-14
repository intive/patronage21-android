package com.intive.repository.network

import com.google.gson.JsonObject
import com.intive.repository.domain.model.UserRegistration
import com.intive.repository.network.response.RegistrationResponse
import retrofit2.Response
import retrofit2.http.*

interface RegistrationServiceJava {
    @POST("api/users")
    suspend fun sendDataFromRegistrationForm(
        @Body user: UserRegistration
    ): Response<RegistrationResponse>
}