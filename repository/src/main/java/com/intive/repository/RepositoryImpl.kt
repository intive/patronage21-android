package com.intive.repository

import com.intive.repository.domain.model.User
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val mapper: UserDtoMapper
) : Repository {

    override suspend fun getCandidates(
        page: Int
    ): List<User> {
        return networkRepository.getCandidates(page)
            .users.map { user ->
            mapper.mapToDomainModel(user)
        }
    }

    override suspend fun getLeaders(page: Int): List<User> {
        TODO("Not yet implemented")
    }
}