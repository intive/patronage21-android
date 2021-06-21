package com.intive.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import com.google.gson.JsonObject
import com.intive.repository.database.DatabaseRepository
import com.intive.repository.database.audits.AuditEntity
import com.intive.repository.database.technologies.TechnologyEntity
import com.intive.repository.database.util.AuditEntityMapper
import com.intive.repository.domain.ListUserJava
import com.intive.repository.domain.model.*
import com.intive.repository.network.NetworkRepository
import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.local.LocalRepository
import com.intive.repository.network.response.*
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.EventInviteResponseDtoMapper
import com.intive.repository.network.util.*
import retrofit2.Response
import java.time.OffsetDateTime

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    userMapper: UserDtoMapper,
    auditMapper: AuditDtoMapper,
    auditEntityMapper: AuditEntityMapper,
    private val eventMapper: EventDtoMapper,
    private val inviteResponseMapper: EventInviteResponseDtoMapper,
    private val newEventMapper: NewEventDtoMapper,
    private val editEventMapper: EditEventDtoMapper,
    private val stageDetailsMapper: StageDetailsDtoMapper,
    private val stageDtoMapper: StageDtoMapper,
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

    override suspend fun getUsersJava(
        role: String,
        group: String?
    ): UsersResponseJava {
        return networkRepository.getUsersJava(
            role = role,
            group = group
        )
    }

    override suspend fun getUsersJava(
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?
    ): UsersResponseJava {
        return networkRepository.getUsersJava(
            role = role,
            group = group,
            firstName = firstName,
            lastName = lastName
        )
    }


    override suspend fun getUsersJava(
        role: String,
        group: String?,
        query: String,
    ): UsersResponseJava {

        return when {
            query.isBlank() -> {
                networkRepository.getUsersJava(
                    role = role,
                    group = group
                )
            }
            query.split(" ").size == 1 -> {
                networkRepository.getUsersJava(
                    role = role,
                    group = group,
                    query = query
                )
            }
            else -> {
                val q = query.split(" ")
                networkRepository.getUsersJava(
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
        val response = getUsersJava(role = role, group = group)
        return response.users.size
    }

    override suspend fun deactivateUser(login: String): Response<String> {
        return networkRepository.deactivateUser(login)
    }

    override suspend fun updateUser(user: User): Response<String> {
        return networkRepository.updateUser(user)
    }

    override val auditsMapper: AuditDtoMapper = auditMapper
    override val auditsEntityMapper: AuditEntityMapper = auditEntityMapper

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchAudits(page: Int, query: String, sortBy: String): AuditResponse {
        return networkRepository.searchAudits(page, query, sortBy)
    }

    override fun getAllAuditsAsc(): PagingSource<Int, AuditEntity> {
        return databaseRepository.getAllAuditsAsc()
    }

    override fun getAllAuditsDesc(): PagingSource<Int, AuditEntity> {
        return databaseRepository.getAllAuditsDesc()
    }

    override fun searchAuditsAsc(query: String): PagingSource<Int, AuditEntity> {
        return databaseRepository.searchAuditsAsc(query)
    }

    override fun searchAuditsDesc(query: String): PagingSource<Int, AuditEntity> {
        return databaseRepository.searchAuditsDesc(query)
    }

    override suspend fun insertAudit(title: String, date: OffsetDateTime, userName: String) {
        databaseRepository.insert(auditsEntityMapper.mapToEntityModel(Audit("", title, date, userName)))
    }

    override suspend fun getTechnologies(): List<String> {

        when {
            isCachingEnabled() && databaseRepository.getTechnologiesCount() > 0 -> {
                val technologyEntityList = databaseRepository.getAllTechnologies()
                return technologyEntityList.map { it.name }
            }
            else -> {
                val technologiesList = networkRepository.getTechnologies().groups

                if (technologiesList.isNotEmpty()) {
                    enableCaching()
                    databaseRepository.clearTechnologiesTable()
                    technologiesList.forEach {
                        databaseRepository.insert(TechnologyEntity(0, it))
                    }
                }

                return technologiesList
            }
        }

    }

    override suspend fun getTechnologyGroups(): List<GroupParcelable> {
        return networkRepository.getTechnologyGroups()
    }

    override suspend fun sendDataFromRegistrationForm(user: UserRegistration): Response<RegistrationResponse> {
        return networkRepository.sendDataFromRegistrationForm(user)
    }

    override suspend fun sendCodeToServer(code: String, email: String): Response<String> {
        val body = JsonObject()
        body.addProperty("code", code)
        body.addProperty("email", email)
        return networkRepository.sendCodeToServer(body)
    }

    override suspend fun getEvents(dateStart: String, dateEnd: String): List<Event> {
        return networkRepository.getEvents(dateStart, dateEnd).map { event ->
            eventMapper.mapToDomainModel(event)
        }
    }

    override suspend fun getEventUsers(): List<ListUserJava> {
        return networkRepository.getEventUsers()
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

    override suspend fun addNewEvent(event: NewEvent): Response<Any> {
        return networkRepository.addNewEvent(newEventMapper.mapFromDomainModel(event))
    }

    override suspend fun editEvent(event: EditEvent, id: String): Response<String> {
        return networkRepository.editEvent(editEventMapper.mapFromDomainModel(event), id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getStages(groupId: String): List<Stage> {
        return stageDtoMapper.toDomainList(networkRepository.getStages(groupId))
    }

    override suspend fun addGroup(group: GroupParcelable): Response<String> {
        val bodyGroup = JsonObject()
        bodyGroup.addProperty("id", group.id)
        bodyGroup.addProperty("name", group.name)
        bodyGroup.addProperty("description", group.description)
        bodyGroup.addProperty("technologies", group.technologies.toString())
        return networkRepository.addGroup(bodyGroup)
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

    override suspend fun deleteEvent(id: String): Response<String> {
        return networkRepository.deleteEvent(id)
    }

}