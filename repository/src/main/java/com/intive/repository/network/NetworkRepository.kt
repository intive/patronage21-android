package com.intive.repository.network

import com.intive.repository.network.model.UserDto
import com.intive.repository.network.response.AuditResponse

class NetworkRepository(
        private val usersService: UsersService,
        private val auditService: AuditService,
        private val technologyGroupsService: TechnologyGroupsService
) {
    suspend fun getUsers(): List<UserDto> {
        return usersService.getUsers()
    }

    suspend fun searchAudits(page: Int, query: String): AuditResponse{
        return auditService.searchAudits(page, query)
    }
  
    suspend fun getTechnologyGroups(): List<String> {
        return technologyGroupsService.getTechGroups()
    }
}