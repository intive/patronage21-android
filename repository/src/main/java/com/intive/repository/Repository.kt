package com.intive.repository


import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.Group
import com.intive.repository.domain.model.User
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.UserDtoMapper
import kotlinx.coroutines.flow.Flow

interface Repository {
    val usersMapper: UserDtoMapper
    suspend fun getTechnologies(): List<String>
    suspend fun getTechnologyGroups(): List<Group>
    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse
    suspend fun getTotalUsersByRole(role: String): Int
    suspend fun getEvents(dateStart: String, dateEnd: String): List<Event>
    suspend fun searchAudits(page: Int, query: String): List<Audit>
}