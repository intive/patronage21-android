package com.intive.repository.network

import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.model.EventDto
import com.intive.repository.network.response.UsersResponse

class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService
) {
    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse {
        return usersService.getUsersByRole(role = role, page = page)
    }

    suspend fun searchAudits(page: Int, query: String): AuditResponse{
        return auditService.searchAudits(page, query)
    }

    suspend fun getTechnologyGroups(): List<String> {
        return technologyGroupsService.getTechGroups()
    }

    suspend fun getEvents(dateStart: String, dateEnd: String): List<EventDto> {
        return eventsService.getEvents(dateStart, dateEnd)
    }
}