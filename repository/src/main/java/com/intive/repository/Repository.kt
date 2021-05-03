package com.intive.repository


import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.NewEvent
import com.intive.repository.domain.model.Group
import com.intive.repository.domain.model.User
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.UserDtoMapper
import kotlinx.coroutines.flow.Flow

interface Repository {
    val usersMapper: UserDtoMapper

    //suspend fun getAudits(): List<Audit>

    suspend fun getTechnologies(): List<String>
    suspend fun getTechnologyGroups(): List<Group>

    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse
    suspend fun getTotalUsersByRole(role: String): Int
    suspend fun searchAudits(page: Int, query: String): List<Audit>

    suspend fun addNewEvent(event: NewEvent): Response<String>
    suspend fun getEvents(dateStart: String, dateEnd: String): List<Event>

    suspend fun sendDataFromRegistrationForm(user: UserRegistration) : Response<String>
    suspend fun sendCodeToServer(code: String, email: String): Response<String>
    suspend fun sendRequestForCode(email: String)

}