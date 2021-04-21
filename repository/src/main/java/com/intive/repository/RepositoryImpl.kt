package com.intive.repository

import android.util.Log
import com.intive.repository.domain.model.Event2
import com.intive.repository.domain.model.User
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.EventDtoMapper
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val userMapper: UserDtoMapper,
    private val eventMapper: EventDtoMapper
) : Repository {

    override suspend fun getUsers(): List<User> {
        return networkRepository.getUsers().map { user ->
            userMapper.mapToDomainModel(user)
        }
    }

    override suspend fun getEvents(dateStart: String, dateEnd: String): List<Event2> {

        return networkRepository.getEvents(dateStart, dateEnd).map { event ->
            eventMapper.mapToDomainModel(event)
        }

    }
}