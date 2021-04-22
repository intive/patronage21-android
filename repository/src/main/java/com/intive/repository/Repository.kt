package com.intive.repository

import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.User

interface Repository {
    suspend fun getUsers(): List<User>
    suspend fun getEvents(dateStart: String, dateEnd: String): List<Event>
}