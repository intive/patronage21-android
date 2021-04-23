package com.intive.repository.network

import com.intive.repository.network.model.AuditDto
import com.intive.repository.network.model.UserDto
import com.intive.repository.network.response.UsersResponse

class NetworkRepository(
        private val usersService: UsersService,
        private val auditService: AuditService,
        private val technologyGroupsService: TechnologyGroupsService
) {
    suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse {
        return usersService.getUsersByRole(role = role, page = page)
    }

    suspend fun getAudits(): List<AuditDto>{
        return auditService.getAudits()
    }
  
    suspend fun getTechnologyGroups(): List<String> {
        return technologyGroupsService.getTechGroups()
    }
}