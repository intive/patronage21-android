package com.intive.repository.network


import com.google.gson.JsonObject
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import com.intive.repository.domain.model.Group
import com.intive.repository.network.model.*
import com.intive.repository.network.response.GradebookResponse
import com.intive.repository.network.response.UsersResponse



class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService,
    private val registrationService: RegistrationService,
    private val technologyGroupsServiceJava: TechnologyGroupsServiceJava,
    private val gradebookService: GradebookService
) {
    suspend fun getUsersByRole(
        page: Int,
        role: String,
        group: String?
    ): UsersResponse {
        return usersService.getUsersByRole( page = page, role = role, group = group)
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

    suspend fun getGradebook(
        group: String,
        sortby: String,
        page: Int
    ): GradebookResponse {
        return gradebookService.getGradebook(group = group, sortby=sortby, page = page)
    }
}


