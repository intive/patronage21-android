package com.intive.repository.network

import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.UserDto

class NetworkRepository(private val usersService: UsersService, private val eventsService: EventsService) {
    suspend fun getUsers(): List<UserDto> {
        return usersService.getUsers()
    }

    suspend fun getEvents(dateStart: String, dateEnd: String): List<EventDto> {
        return eventsService.getEvents(dateStart, dateEnd)
    }
}