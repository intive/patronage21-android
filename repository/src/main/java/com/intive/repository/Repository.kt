package com.intive.repository

import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.User
import com.intive.repository.network.response.AuditResponse
import com.intive.repository.network.util.AuditDtoMapper

interface Repository {
    val auditsMapper: AuditDtoMapper
    suspend fun getUsers(): List<User>

    suspend fun getTechnologyGroups(): List<String>

    suspend fun searchAudits(page: Int, query: String): AuditResponse
}