package com.intive.repository.network


import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.AuditDto
import com.intive.repository.network.response.UsersResponse


class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologiesService: TechnologiesService,
    private val eventsService: EventsService
) {
    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse {
        return usersService.getUsersByRole(role = role, page = page)
    }

    suspend fun searchAudits(page: Int, query: String): List<AuditDto>{
        return auditService.searchAudits(page, query).audits
    }

    suspend fun getTechnologies(): List<String> {
        return technologiesService.getTechnologies()
    }

    suspend fun getEvents(dateStart: String, dateEnd: String): List<EventDto> {
        return eventsService.getEvents(dateStart, dateEnd)
    }
}