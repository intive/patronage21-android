package com.intive.repository.network

import com.intive.repository.network.model.UpdateUserDto
import com.intive.repository.network.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UsersServiceJava {
    @GET("api/users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): UserResponse

    @PATCH("api/users/{login}/deactivate")
    suspend fun deactivateUser(
        @Path("login") login: String
    ): Response<String>

    @PUT("api/users")
    suspend fun updateUser(
        @Body user: UpdateUserDto
    ): Response<String>
}