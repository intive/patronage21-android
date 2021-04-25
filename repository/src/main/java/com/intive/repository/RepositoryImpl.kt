package com.intive.repository

import com.intive.repository.domain.model.Audit
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    userMapper: UserDtoMapper,
    private val auditMapped: AuditDtoMapper
) : Repository {

    override suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse {
        return networkRepository.getUsersByRole(role, page)
    }

    override val usersMapper: UserDtoMapper = userMapper
    
    override suspend fun getAudits(): List<Audit> {
        return networkRepository.getAudits().map { audit ->
            auditMapped.mapToDomainModel(audit)
        }
    }

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }
        
}