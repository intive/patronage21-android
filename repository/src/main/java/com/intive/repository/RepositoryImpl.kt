package com.intive.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.intive.repository.domain.model.User
import com.intive.repository.network.CandidatesSource
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.USERS_PAGE_SIZE
import com.intive.repository.network.util.UserDtoMapper
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val mapper: UserDtoMapper
) : Repository {

    override suspend fun getUsersByRole(
        role: String
    ): Flow<PagingData<User>> = Pager(PagingConfig(pageSize = USERS_PAGE_SIZE)) {
        CandidatesSource(networkRepository, mapper, role)
    }.flow
}