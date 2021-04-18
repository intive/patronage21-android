package com.intive.repository.network

import com.intive.repository.network.model.UserDto

class NetworkRepository(private val usersService: UsersService) {
    suspend fun getUsers(): List<UserDto> {
        return usersService.getUsers()
    }
}