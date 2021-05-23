package com.intive.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.JsonObject
import com.intive.repository.database.DatabaseRepository
import com.intive.repository.database.technologies.TechnologyEntity
import com.intive.repository.domain.model.*
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.local.LocalRepository
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.EventInviteResponseDtoMapper
import com.intive.repository.network.response.GradebookResponse
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.*
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    userMapper: UserDtoMapper,
    auditMapper: AuditDtoMapper,
    private val eventMapper: EventDtoMapper,
    private val inviteResponseMapper: EventInviteResponseDtoMapper,
    private val newEventMapper: NewEventDtoMapper,
    private val stageDetailsMapper: StageDetailsDtoMapper,
    gbMapper: GradebookDtoMapper,
    private val localRepository: LocalRepository,
    private val databaseRepository: DatabaseRepository
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

    override suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        query: String,
    ): UsersResponse {

        return when {
            query.isBlank() -> {
                networkRepository.getUsers(
                    page = page,
                    role = role,
                    group = group
                )
            }
            query.split(" ").size == 1 -> {
                networkRepository.getUsers(
                    page = page,
                    role = role,
                    group = group,
                    firstName = query,
                    lastName = query,
                    login = query
                )
            }
            else -> {
                val q = query.split(" ")
                networkRepository.getUsers(
                    page = page,
                    role = role,
                    group = group,
                    firstName = q[0],
                    lastName = q[1]
                )
            }
        }
    }

    override suspend fun getUser(login: String): User {
        return usersMapper.mapToDomainModel(
            networkRepository.getUser(login).user
        )
    }

    override suspend fun getTotalUsersByRole(role: String, group: String?): Int {
        val response = getUsers(role = role, group = group, page = 1)
        return response.totalSize
    }

    override suspend fun deactivateUser(login: String): Response<String> {
        return networkRepository.deactivateUser(login)
    }

    override val auditsMapper: AuditDtoMapper = auditMapper

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchAudits(page: Int, query: String, sortBy: String): AuditResponse {
        return networkRepository.searchAudits(page, query, sortBy)
    }

    override suspend fun getTechnologies(): List<String> {

        when (isCachingEnabled()) {
            true -> {
                val technologyEntityList = databaseRepository.getAllTechnologies()
                return technologyEntityList.map { it.name }
            }
            false -> {
                enableCaching()
                val technologiesList = networkRepository.getTechnologies().groups
                technologiesList.forEach {
                    databaseRepository.insert(TechnologyEntity(0, it))
                }

                return technologiesList
            }
        }

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
        return networkRepository.updateInviteResponse(
            inviteResponseMapper.mapFromDomainModel(
                inviteResponse
            )
        )
    }

    override suspend fun sendRequestForCode(email: String) {
        val body = JsonObject()
        body.addProperty("email", email)
        networkRepository.sendRequestForCode(body)
    }

    override suspend fun addNewEvent(event: NewEvent): Response<String> {
        return networkRepository.addNewEvent(newEventMapper.mapFromDomainModel(event))

    }

    override suspend fun getStageDetails(id: Long): StageDetails {
        return stageDetailsMapper.mapToDomainModel(networkRepository.getStageDetails(id))
    }

    override val gradebookMapper: GradebookDtoMapper = gbMapper

    override suspend fun getGradebook(
        group: String,
        sortby: String,
        page: Int
    ): GradebookResponse {
        return networkRepository.getGradebook(group = group, sortby = sortby, page = page)
    }

    override fun isUserLogged(): Boolean {
        return localRepository.isUserLogged()
    }

    override fun getUserLoginOrNull(): String? {
        return localRepository.getUserLoginOrNull()
    }

    override fun loginUser(login: String) {
        localRepository.loginUser(login)
    }

    override fun logoutUser() {
        localRepository.logoutUser()
    }

    override fun enableCaching() {
        localRepository.enableCaching()
    }

    override fun isCachingEnabled(): Boolean {
        return localRepository.isCachingEnabled()
    }

}