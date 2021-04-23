package com.intive.repository

import androidx.paging.PagingData
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getTechnologyGroups(): List<String>
    suspend fun getAudits(): List<Audit>
    suspend fun getUsersByRole(
        role: String
    ): Flow<PagingData<User>>
}