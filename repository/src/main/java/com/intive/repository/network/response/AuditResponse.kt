package com.intive.repository.network.response

import com.intive.repository.network.model.AuditDto

data class AuditResponse(
    val next_page: Int,
    val previous_page: Any,
    val size: Int,
    val audits: List<AuditDto>
)