package com.intive.repository.network

import com.intive.repository.network.model.UserDto
import retrofit2.http.GET

interface UsersService {

    @GET("api/users")
    suspend fun getUsers(): List<UserDto>
}