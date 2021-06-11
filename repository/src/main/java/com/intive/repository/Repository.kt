package com.intive.repository

import androidx.paging.PagingSource
import com.intive.repository.database.audits.AuditEntity
import com.intive.repository.database.util.AuditEntityMapper
import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.EventInviteResponse
import com.intive.repository.domain.model.NewEvent
import com.intive.repository.domain.model.UserRegistration
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.domain.model.*
import com.intive.repository.network.response.GradebookResponse
import retrofit2.Response
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.GradebookDtoMapper
import com.intive.repository.network.util.UserDtoMapper
import java.time.OffsetDateTime

interface Repository {

    val usersMapper: UserDtoMapper
    val auditsMapper: AuditDtoMapper
    val gradebookMapper: GradebookDtoMapper
    val auditsEntityMapper: AuditEntityMapper

    suspend fun getTechnologies(): List<String>
    suspend fun getTechnologyGroups(): List<GroupParcelable>

    suspend fun getUsers(page: Int, role: String, group: String?): UsersResponse
    suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?
    ): UsersResponse

    suspend fun getUsers(
        page: Int,
        role: String,
        group: String?,
        firstName: String?,
        lastName: String?,
        login: String?
    ): UsersResponse

    suspend fun getUsers(page: Int, role: String, group: String?, query: String): UsersResponse
    suspend fun getTotalUsersByRole(role: String, group: String?): Int
    suspend fun getUser(login: String): User
    suspend fun deactivateUser(login: String): Response<String>
    suspend fun updateUser(user: User): Response<String>

    fun getAllAuditsAsc(): PagingSource<Int, AuditEntity>
    fun getAllAuditsDesc(): PagingSource<Int, AuditEntity>
    suspend fun searchAudits(page: Int, query: String, sortBy: String): AuditResponse
    fun searchAuditsAsc(query: String): PagingSource<Int, AuditEntity>
    fun searchAuditsDesc(query: String): PagingSource<Int, AuditEntity>
    suspend fun insertAudit(title: String, date: OffsetDateTime, userName: String)

    suspend fun addNewEvent(event: NewEvent): Response<Any>
    suspend fun getEvents(dateStart: String, dateEnd: String, userId: Long): List<Event>
    suspend fun editEvent(event: EditEvent, id: Long): Response<String>
    suspend fun deleteEvent(id: Long): Response<String>

    suspend fun sendDataFromRegistrationForm(user: UserRegistration): Response<String>
    suspend fun sendCodeToServer(code: String, email: String): Response<String>
    suspend fun sendRequestForCode(email: String)
    suspend fun updateInviteResponse(inviteResponse: EventInviteResponse): Response<String>

    suspend fun getStages(groupId: String): List<Stage>
    suspend fun addGroup(group: GroupParcelable) : Response<String>
    suspend fun getStageDetails(id: Long): StageDetails
    suspend fun getGradebook(group: String, sortby: String, page: Int): GradebookResponse

    fun isUserLogged(): Boolean
    fun getUserLoginOrNull(): String?
    fun loginUser(login: String)
    fun logoutUser()

    fun enableCaching()
    fun isCachingEnabled(): Boolean

}

