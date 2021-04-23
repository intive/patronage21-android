package com.intive.repository

import com.intive.repository.domain.model.Event
import com.intive.repository.domain.model.User
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.util.EventDtoMapper
import com.intive.repository.domain.model.Audit
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val userMapper: UserDtoMapper,
    private val auditMapped: AuditDtoMapper,
    private val eventMapper: EventDtoMapper
) : Repository {

    override suspend fun getUsers(): List<User> {
        return networkRepository.getUsers().map { user ->
            userMapper.mapToDomainModel(user)
        }
    }

    override suspend fun getEvents(dateStart: String, dateEnd: String): List<Event> {
        return networkRepository.getEvents(dateStart, dateEnd).map { event ->
            eventMapper.mapToDomainModel(event)
        }

    }

    override suspend fun getAudits(): List<Audit> {
        return networkRepository.getAudits().map { audit ->
            auditMapped.mapToDomainModel(audit)
        }
    }

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }
}