package com.intive.repository.network

import com.intive.repository.network.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val ROLE_CANDIDATE = "candidate"
const val ROLE_LEADER = "leader"
const val USERS_PAGE_SIZE = 24

interface UsersService {

    @GET("api/users")
    suspend fun getUsersByRole(
        @Query("page") page: Int,
        @Query("role") role: String,
        @Query("group") group: String?
    ): UsersResponse

}