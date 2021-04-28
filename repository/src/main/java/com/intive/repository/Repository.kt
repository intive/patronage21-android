package com.intive.repository


import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.User
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response

interface Repository {
    suspend fun getUsers(): List<User>
    suspend fun getTechnologyGroups(): List<String>
    //suspend fun getAudits(): List<Audit>
    suspend fun searchAudits(page: Int, query: String): List<Audit>
    suspend fun getEvents(dateStart: String, dateEnd: String): List<Event>
    suspend fun sendDataFromRegistrationForm(user: UserRegistration) : Response<String>

}