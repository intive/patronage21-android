package com.intive.repository.network

import com.intive.repository.network.model.AuditDto
import com.intive.repository.network.model.UserDto
import com.intive.repository.domain.model.UserRegistration
import retrofit2.Response

class NetworkRepository(
    private val usersService: UsersService,
    private val auditService: AuditService,
    private val technologyGroupsService: TechnologyGroupsService
) {
    suspend fun getUsers(): List<UserDto> {
        return usersService.getUsers()
    }

    suspend fun getAudits(): List<AuditDto> {
        return auditService.getAudits()
    }

    suspend fun getTechnologyGroups(): List<String> {
        return technologyGroupsService.getTechGroups()
    }

    suspend fun sendDataFromRegistrationForm(user: UserRegistration): Response<String> {
        return technologyGroupsService.sendDataFromRegistrationForm(user)
    }
}