package com.intive.repository.network


import com.intive.repository.domain.model.Group
import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.AuditDto
import com.intive.repository.network.response.UsersResponse


class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService
) {
    suspend fun getUsersByRole(
        page: Int,
        role: String,
        group: String?
    ): UsersResponse {
        return usersService.getUsersByRole( page = page, role = role, group = group)
    }

    suspend fun searchAudits(page: Int, query: String): List<AuditDto>{
        return auditService.searchAudits(page, query).audits
    }

    suspend fun getTechnologies(): List<String> {
        return technologyGroupsService.getTechnologies()
    }

    suspend fun getTechnologyGroups(): List<Group> {
        return technologyGroupsService.getTechnologyGroups()
    }

    suspend fun getEvents(dateStart: String, dateEnd: String): List<EventDto> {
        return eventsService.getEvents(dateStart, dateEnd)
    }
}