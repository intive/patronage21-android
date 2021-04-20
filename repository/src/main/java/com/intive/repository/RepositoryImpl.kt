package com.intive.repository

import com.intive.repository.domain.model.User
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val mapper: UserDtoMapper
) : Repository {
    override suspend fun getUsers(): List<User> {
        return networkRepository.getUsers().map { user ->
            mapper.mapToDomainModel(user)
        }
    }

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }
}