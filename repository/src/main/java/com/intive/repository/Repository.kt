package com.intive.repository

import androidx.paging.PagingData
import com.intive.repository.domain.model.User
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getUsersByRole(
        role: String
    ): Flow<PagingData<User>>
}