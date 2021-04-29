package com.intive.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.intive.repository.domain.model.User
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val userMapper: UserDtoMapper,
    auditMapper: AuditDtoMapper
) : Repository {
    override suspend fun getUsers(): List<User> {
        return networkRepository.getUsers().map { user ->
            userMapper.mapToDomainModel(user)
        }
    }

    override val auditsMapper: AuditDtoMapper = auditMapper

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchAudits(page: Int, query: String): AuditResponse {
        return networkRepository.searchAudits(page, query)
    }

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }
}