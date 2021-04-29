package com.intive.repository


import com.intive.repository.domain.model.Event
import android.os.Build
import androidx.annotation.RequiresApi
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.util.EventDtoMapper
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.response.UsersResponse
import com.intive.repository.network.util.UserDtoMapper

class RepositoryImpl(
    private val networkRepository: NetworkRepository,
    userMapper: UserDtoMapper,
    auditMapper: AuditDtoMapper,
    private val eventMapper: EventDtoMapper
) : Repository {
    
    override val usersMapper: UserDtoMapper = userMapper

    override suspend fun getUsersByRole(
        role: String,
        page: Int
    ): UsersResponse {
        return networkRepository.getUsersByRole(role, page)
    }

    override suspend fun getTotalUsersByRole(role: String): Int {
        val response = getUsersByRole(role, 1)
        return response.totalSize
    }

    override val auditsMapper: AuditDtoMapper = auditMapper

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchAudits(page: Int, query: String): AuditResponse {
        return networkRepository.searchAudits(page, query)
    }

    override suspend fun getTechnologyGroups(): List<String> {
        return networkRepository.getTechnologyGroups()
    }

    override suspend fun getEvents(dateStart: String, dateEnd: String): List<Event> {
        return networkRepository.getEvents(dateStart, dateEnd).map { event ->
            eventMapper.mapToDomainModel(event)
        }
    }
}