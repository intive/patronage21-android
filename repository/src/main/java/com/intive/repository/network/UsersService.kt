package com.intive.repository.network

import com.google.gson.JsonObject
import com.intive.repository.network.model.UserDto
import com.intive.repository.network.response.UsersResponse
import retrofit2.Response
import retrofit2.http.*

const val ROLE_CANDIDATE = "candidate"
const val ROLE_LEADER = "leader"
const val USERS_PAGE_SIZE = 24

interface UsersService {

    @GET("api/users")
    suspend fun getUsersByRole(
        @Query("page") page: Int,
        @Query("role") role: String,
        @Query("group") group: String?,
        @Query("firstName") firstName: String?,
        @Query("lastName") lastName: String?,
        @Query("login") login: String?,
    ): UsersResponse

    @GET("api/users")
    suspend fun getUser(
        @Query("login") login: String
    ): UserDto

    @PATCH("api/users/{login}/deactivate")
    suspend fun deactivateUser(
        @Path("login") login: String
    ): Response<String>
}