package com.intive.repository.network


import com.google.gson.JsonObject
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import com.intive.repository.domain.model.Group
import com.intive.repository.network.model.EventDto
import com.intive.repository.network.model.AuditDto

import com.intive.repository.network.model.EventInviteResponseDto
import com.intive.repository.network.model.NewEventDto
import com.intive.repository.network.response.UsersResponse



class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService,
    private val registrationService: RegistrationService
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

//    suspend fun getAudits(): List<AuditDto> {
//        return auditService.getAudits()
//    }

    suspend fun searchAudits(page: Int, query: String): List<AuditDto> {
        return auditService.searchAudits(page, query).audits
    }

    suspend fun getTechnologies(): List<String> {
        return technologyGroupsService.getTechnologies()
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

    suspend fun updateInviteResponse(inviteResponse: EventInviteResponseDto): Response<String>{
        return eventsService.updateInviteResponse(inviteResponse)
    }
}


