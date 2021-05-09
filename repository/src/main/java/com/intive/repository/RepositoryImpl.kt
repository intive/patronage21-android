package com.intive.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonObject
import com.intive.repository.domain.model.*
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.EventDtoMapper
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.NewEventDtoMapper
import com.intive.repository.network.util.UserDtoMapper
import retrofit2.Response

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    userMapper: UserDtoMapper,
    private val auditMapped: AuditDtoMapper,
    private val eventMapper: EventDtoMapper,
    private val newEventMapper: NewEventDtoMapper
) : Repository {

    override val usersMapper: UserDtoMapper = userMapper

    override suspend fun getUsers(
        page: Int,
        role: String,
        group: String?
    ): UsersResponse {
        return networkRepository.getUsers(
            role = role,
            page = page,
            group = group
        )
    }

    override suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?
    ): UsersResponse {
        return networkRepository.getUsers(
            role = role,
            page = page,
            group = group,
            firstName = firstName,
            lastName = lastName
        )
    }

    override suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?,
        login: String?
    ): UsersResponse {
        return networkRepository.getUsers(
            role = role,
            page = page,
            group = group,
            firstName = firstName,
            lastName = lastName,
            login = login
        )
    }

    override suspend fun getTotalUsersByRole(role: String, group: String?): Int {
        val response = getUsers(role = role, group = group, page = 1)
        return response.totalSize
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchAudits(page: Int, query: String): List<Audit> {
        return networkRepository.searchAudits(page, query).map { audit ->
            auditMapped.mapToDomainModel(audit)
        }
    }

    override suspend fun getTechnologies(): List<String> {
        return networkRepository.getTechnologies()
    }

    override suspend fun getTechnologyGroups(): List<Group> {
        return networkRepository.getTechnologyGroups()
    }

    override suspend fun sendDataFromRegistrationForm(user: UserRegistration): Response<String> {
        return networkRepository.sendDataFromRegistrationForm(user)
    }

    override suspend fun sendCodeToServer(code: String, email: String): Response<String> {
        val body = JsonObject()
        body.addProperty("code", code)
        body.addProperty("email", email)
        return networkRepository.sendCodeToServer(body)
    }

    override suspend fun getEvents(dateStart: String, dateEnd: String, userId: Long): List<Event> {
        return networkRepository.getEvents(dateStart, dateEnd, userId).map { event ->
            eventMapper.mapToDomainModel(event)
        }
    }

    override suspend fun sendRequestForCode(email: String) {
        val body = JsonObject()
        body.addProperty("email", email)
        networkRepository.sendRequestForCode(body)
    }

    override suspend fun addNewEvent(event: NewEvent): Response<String> {
        return networkRepository.addNewEvent(newEventMapper.mapFromDomainModel(event))
    }
}