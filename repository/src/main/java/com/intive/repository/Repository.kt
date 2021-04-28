package com.intive.repository

import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.User
import com.intive.repository.network.response.AuditResponse

interface Repository {
    suspend fun getUsers(): List<User>

    suspend fun getTechnologyGroups(): List<String>

    suspend fun searchAudits(page: Int, query: String): AuditResponse
}