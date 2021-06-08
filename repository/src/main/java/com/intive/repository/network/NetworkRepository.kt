package com.intive.repository.network

import com.intive.repository.network.model.EventDto
import com.google.gson.JsonObject
import com.intive.repository.domain.model.UserRegistration
import com.intive.repository.network.model.*
import retrofit2.Response
import com.intive.repository.domain.model.GroupParcelable
import com.intive.repository.domain.model.User
import com.intive.repository.network.response.*
import com.intive.repository.network.response.UsersResponse
import retrofit2.http.Body
import com.intive.repository.network.response.GradebookResponse
import com.intive.repository.network.response.UserResponse

class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService,
    private val eventsService: EventsService,
    private val registrationService: RegistrationService,
    private val technologyGroupsServiceJava: TechnologyGroupsServiceJava,
    private val stageService: StageService,
    private val stageDetailsService: StageDetailsService,
    private val gradebookService: GradebookService
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

    suspend fun searchAudits(page: Int, query: String, sortBy: String): AuditResponse {
        return auditService.searchAudits(page, query, sortBy)
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

    suspend fun getUser(
        login: String
    ): UserResponse {
        return usersService.getUser(login)
    }

    suspend fun deactivateUser(login: String): Response<String> {
        return usersService.deactivateUser(login)
    }

    suspend fun updateUser(user: User): Response<String> {
        return usersService.updateUser(
            UpdateUserDto(
                firstName = user.firstName,
                lastName = user.lastName,
                login = user.login,
                projects = user.projects,
                email = user.email,
                phoneNumber = user.phoneNumber,
                gitHubUrl = user.github,
                bio = user.bio
            )
        )
    }

    suspend fun getTechnologies(): TechnologiesList {
        return technologyGroupsServiceJava.getTechnologies()
    }

    suspend fun getTechnologyGroups(): List<GroupParcelable> {
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

    suspend fun editEvent(event: EditEventDto, id: Long): Response<String> {
        return eventsService.editEvent(event, id)
    }

    suspend fun deleteEvent(id: Long): Response<String> {
        return eventsService.deleteEvent(id)
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

    suspend fun getStages(groupId: String): List<StageDto> {
        return stageService.getStages(groupId)
    }

    suspend fun addGroup(@Body group: JsonObject) : Response<String> {
        return technologyGroupsService.addGroup(group)
    }

    suspend fun getStageDetails(id: Long): StageDetailsDto {
        return stageDetailsService.getStageDetails(id)
    }

    suspend fun getGradebook(
        group: String,
        sortby: String,
        page: Int
    ): GradebookResponse {
        return gradebookService.getGradebook(group = group, sortby = sortby, page = page)
    }
}

