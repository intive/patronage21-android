package com.intive.repository.network

import com.intive.repository.domain.model.Audit
import com.intive.repository.network.model.AuditDto
import retrofit2.http.GET

interface AuditService {

    @GET("api/audits")
    suspend fun getAudits(): List<AuditDto>
}