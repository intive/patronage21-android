package com.intive.repository.network

import com.intive.repository.network.model.UserDto
import com.intive.repository.network.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {

    @GET("api/users")
    suspend fun getCandidates(
        @Query("role") role: String = "candidate",
        @Query("page") page: Int,
    ): UsersResponse

    @GET("api/users")
    suspend fun getLeaders(
        @Query("role") role: String = "leader",
        @Query("page") page: Int,
    ): UsersResponse
}