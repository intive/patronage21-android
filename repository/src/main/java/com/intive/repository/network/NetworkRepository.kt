package com.intive.repository.network

import com.intive.repository.network.response.UsersResponse

class NetworkRepository(private val usersService: UsersService) {

    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse {
        return usersService.getUsersByRole(role = role, page = page)
    }
}