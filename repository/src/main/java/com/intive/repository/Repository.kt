package com.intive.repository


import com.intive.repository.domain.model.Event
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.UserDtoMapper


interface Repository {
    val usersMapper: UserDtoMapper
    val auditsMapper: AuditDtoMapper
    suspend fun getTechnologyGroups(): List<String>
    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse
    suspend fun getTotalUsersByRole(role: String): Int
    suspend fun getEvents(dateStart: String, dateEnd: String): List<Event>
    suspend fun searchAudits(page: Int, query: String): AuditResponse
}