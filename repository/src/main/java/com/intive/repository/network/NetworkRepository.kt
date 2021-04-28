package com.intive.repository.network


import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.UserDto
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import com.intive.repository.network.model.AuditDto

class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService
) {
    suspend fun getUsers(): List<UserDto> {
        return usersService.getUsers()
    }

    suspend fun getAudits(): List<AuditDto> {
        return auditService.getAudits()

    suspend fun searchAudits(page: Int, query: String): List<AuditDto>{
        return auditService.searchAudits(page, query).audits
    }

    suspend fun getTechnologyGroups(): List<String> {
        return technologyGroupsService.getTechGroups()
    }

    suspend fun sendDataFromRegistrationForm(user: UserRegistration): Response<String> {
        return technologyGroupsService.sendDataFromRegistrationForm(user)

    suspend fun getEvents(dateStart: String, dateEnd: String): List<EventDto> {
        return eventsService.getEvents(dateStart, dateEnd)
    }
}