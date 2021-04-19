package com.intive.repository

import com.intive.repository.domain.model.User

interface Repository {
    suspend fun getCandidates(
        page: Int
    ): List<User>

    suspend fun getLeaders(
        page: Int
    ): List<User>
}