package com.intive.repository


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.JsonObject
import com.intive.repository.domain.model.*
import com.intive.repository.network.NetworkRepository

import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.network.GRADEBOOK_PAGE_SIZE
import com.intive.repository.network.GradebookSource
import com.intive.repository.network.response.GradebookResponse
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    userMapper: UserDtoMapper,
    private val auditMapped: AuditDtoMapper,
    private val eventMapper: EventDtoMapper,
    private val inviteResponseMapper: EventInviteResponseDtoMapper,
    private val newEventMapper: NewEventDtoMapper,
    gbMapper: GradebookDtoMapper
) : Repository {

    override val usersMapper: UserDtoMapper = userMapper

    override suspend fun getUsersByRole(
        page: Int,
        role: String,
        group: String?
    ): UsersResponse {
        return networkRepository.getUsersByRole(role = role, page = page, group = group)
    }

    override suspend fun getTotalUsersByRole(role: String, group: String?): Int {
        val response = getUsersByRole(role = role, group = group, page = 1)
        println(response)
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


    override suspend fun updateInviteResponse(inviteResponse: EventInviteResponse): Response<String> {
        return networkRepository.updateInviteResponse(inviteResponseMapper.mapFromDomainModel(inviteResponse))
    }

    override suspend fun sendRequestForCode(email: String) {
        val body = JsonObject()
        body.addProperty("email", email)
        networkRepository.sendRequestForCode(body)
    }

    override suspend fun addNewEvent(event: NewEvent): Response<String> {
        return networkRepository.addNewEvent(newEventMapper.mapFromDomainModel(event))

    }

    override val gradebookMapper: GradebookDtoMapper = gbMapper

    override suspend fun getGradebook(
        group: String,
        sortby: String,
        page: Int
    ): GradebookResponse {
        return networkRepository.getGradebook(group = group, sortby = sortby, page = page)
    }
}