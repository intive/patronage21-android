package com.intive.repository


import androidx.paging.PagingData
import com.intive.repository.domain.model.*
import com.intive.repository.network.response.GradebookResponse
import retrofit2.Response
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.GradebookDtoMapper
import com.intive.repository.network.util.UserDtoMapper
import kotlinx.coroutines.flow.Flow


interface Repository {

    val usersMapper: UserDtoMapper
    val gradebookMapper: GradebookDtoMapper

    //suspend fun getAudits(): List<Audit>

    suspend fun getTechnologies(): List<String>
    suspend fun getTechnologyGroups(): List<Group>
    suspend fun getUsersByRole(page: Int, role: String, group: String?): UsersResponse
    suspend fun getTotalUsersByRole(role: String,group: String?): Int
    suspend fun searchAudits(page: Int, query: String): List<Audit>

    suspend fun addNewEvent(event: NewEvent): Response<String>
    suspend fun getEvents(dateStart: String, dateEnd: String, userId: Long): List<Event>

    suspend fun sendDataFromRegistrationForm(user: UserRegistration) : Response<String>
    suspend fun sendCodeToServer(code: String, email: String): Response<String>
    suspend fun sendRequestForCode(email: String)
    suspend fun updateInviteResponse(inviteResponse: EventInviteResponse): Response<String>

    suspend fun getGradebook(group: String, sortby: String, page: Int): GradebookResponse
}



