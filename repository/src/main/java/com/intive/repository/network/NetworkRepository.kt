package com.intive.repository.network

import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.model.EventDto
import com.google.gson.JsonObject
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import com.intive.repository.domain.model.Group
import com.intive.repository.network.model.*
import com.intive.repository.network.response.UsersResponse

class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService,
    private val registrationService: RegistrationService,
    private val technologyGroupsServiceJava: TechnologyGroupsServiceJava
) {
    suspend fun getUsers(
        page: Int,
        role: String,
        group: String?
    ): UsersResponse {
        return usersService.getUsersByRole(
            page = page,
            role = role,
            group = group,
            firstName = null,
            lastName = null,
            login = null
        )
    }

    suspend fun searchAudits(page: Int, query: String): AuditResponse {
        return auditService.searchAudits(page, query)
    }

    suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?,
        login: String?
    ): UsersResponse {
        return usersService.getUsersByRole(
            page = page,
            role = role,
            group = group,
            firstName = firstName,
            lastName = lastName,
            login = login
        )
    }

    suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?
    ): UsersResponse {
        return usersService.getUsersByRole(
            page = page,
            role = role,
            group = group,
            firstName = firstName,
            lastName = lastName,
            login = null
        )
    }

    suspend fun getTechnologies(): TechnologiesList {
        return technologyGroupsServiceJava.getTechnologies()
    }

    suspend fun getTechnologyGroups(): List<Group> {
        return technologyGroupsService.getTechnologyGroups()
    }

    suspend fun sendDataFromRegistrationForm(user: UserRegistration): Response<String> {
        return registrationService.sendDataFromRegistrationForm(user)
    }

    suspend fun getEvents(dateStart: String, dateEnd: String, userId: Long): List<EventDto> {
        return eventsService.getEvents(dateStart, dateEnd, userId)
    }


    suspend fun addNewEvent(event: NewEventDto): Response<String> {
        return eventsService.addNewEvent(event)
    }

    suspend fun sendCodeToServer(body: JsonObject): Response<String> {
        return registrationService.sendCodeToServer(body)
    }

    suspend fun sendRequestForCode(body: JsonObject) {
        return registrationService.sendRequestForCode(body)
    }

    suspend fun updateInviteResponse(inviteResponse: EventInviteResponseDto): Response<String> {
        return eventsService.updateInviteResponse(inviteResponse)
    }
}



