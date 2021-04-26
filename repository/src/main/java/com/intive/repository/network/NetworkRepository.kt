package com.intive.repository.network

import com.intive.repository.network.model.AuditDto
import com.intive.repository.network.model.UserDto

class NetworkRepository(
        private val usersService: UsersService,
        private val auditService: AuditService,
        private val technologyGroupsService: TechnologyGroupsService
) {
    suspend fun getUsers(): List<UserDto> {
        return usersService.getUsers()
    }

    suspend fun searchAudits(page: Int, query: String): List<AuditDto>{
        return auditService.searchAudits(page, query).audits
    }
  
    suspend fun getTechnologyGroups(): List<String> {
        return technologyGroupsService.getTechGroups()
    }
}