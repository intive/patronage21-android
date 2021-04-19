package com.intive.repository.network

import com.intive.repository.network.response.UsersResponse

class NetworkRepository(private val usersService: UsersService) {
    suspend fun getCandidates(
        page: Int
    ): UsersResponse {
        return usersService.getCandidates(page = page)
    }
}