package com.intive.repository

import com.intive.repository.domain.model.Audit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.intive.repository.domain.model.User
import com.intive.repository.domain.model.Gradebook
import com.intive.repository.network.UsersSource
import com.intive.repository.network.GradebookSource
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.USERS_PAGE_SIZE
import com.intive.repository.network.GRADEBOOK_PAGE_SIZE
import com.intive.repository.network.util.UserDtoMapper
import com.intive.repository.network.util.GradebookDtoMapper
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val userMapper: UserDtoMapper,
    private val gradebookMapper: GradebookDtoMapper,
    private val auditMapped: AuditDtoMapper
) : Repository {

    override suspend fun getUsersByRole(
        role: String
    ): Flow<PagingData<User>> = Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        UsersSource(networkRepository, userMapper, role)
    }.flow

    override suspend fun getAudits(): List<Audit> {
        return networkRepository.getAudits().map { audit ->
            auditMapped.mapToDomainModel(audit)
        }
    }

    override suspend fun getGradebook(): Flow<PagingData<Gradebook>> =
        Pager(PagingConfig(pageSize = GRADEBOOK_PAGE_SIZE)) {
            GradebookSource(networkRepository, gradebookMapper)
        }.flow

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }

}