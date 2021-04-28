package com.intive.repository.network

import com.intive.repository.network.response.AuditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuditService {

    @GET("api/audits")
    suspend fun searchAudits(
        @Query("page") page: Int,
        @Query("query") query: String
    ): AuditResponse
}