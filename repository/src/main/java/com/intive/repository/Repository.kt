package com.intive.repository

import com.intive.repository.domain.model.*
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.util.AuditDtoMapper
import retrofit2.Response
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.UserDtoMapper

interface Repository {

    val usersMapper: UserDtoMapper
    val auditsMapper: AuditDtoMapper

    suspend fun getTechnologies(): List<String>
    suspend fun getTechnologyGroups(): List<Group>

    suspend fun getUsers(page: Int, role: String, group: String?): UsersResponse
    suspend fun getUsers(page: Int, role: String, group: String?, firstName: String?, lastName: String?): UsersResponse
    suspend fun getUsers(page: Int, role: String, group: String?, firstName: String?, lastName: String?, login: String?): UsersResponse
    suspend fun getUsers(page: Int, role: String, group: String?, query: String): UsersResponse
    suspend fun getTotalUsersByRole(role: String, group: String?): Int
    suspend fun getUser(login: String): User
    suspend fun deactivateUser(login: String): Response<String>

    suspend fun searchAudits(page: Int, query: String): AuditResponse

    suspend fun addNewEvent(event: NewEvent): Response<String>
    suspend fun getEvents(dateStart: String, dateEnd: String, userId: Long): List<Event>

    suspend fun sendDataFromRegistrationForm(user: UserRegistration) : Response<String>
    suspend fun sendCodeToServer(code: String, email: String): Response<String>
    suspend fun sendRequestForCode(email: String)
    suspend fun updateInviteResponse(inviteResponse: EventInviteResponse): Response<String>
}