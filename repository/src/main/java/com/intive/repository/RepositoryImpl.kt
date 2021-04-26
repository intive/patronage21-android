package com.intive.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.User
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val userMapper: UserDtoMapper,
    private val auditMapped: AuditDtoMapper
) : Repository {
    override suspend fun getUsers(): List<User> {
        return networkRepository.getUsers().map { user ->
            userMapper.mapToDomainModel(user)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchAudits(page: Int, query: String): List<Audit> {
        return networkRepository.searchAudits(page, query).map { audit ->
            auditMapped.mapToDomainModel(audit)
        }
    }

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }
}