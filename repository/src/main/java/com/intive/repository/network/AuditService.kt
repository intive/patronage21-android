package com.intive.repository.network

import com.intive.repository.domain.model.Audit
import com.intive.repository.network.model.AuditDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AuditService {

    @GET("api/audits")
    suspend fun searchAudits(
        @Query("page") page: Int,
        @Query("query") query: String
    ): List<AuditDto>
}