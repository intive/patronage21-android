package com.intive.repository.network.response

import com.google.gson.annotations.SerializedName
import com.intive.repository.network.model.AuditDto

data class AuditResponse(
    @SerializedName("next_page")
    val nextPage: Int?,
    @SerializedName("previous_page")
    val previousPage: Int?,
    val size: Int,
    val audits: List<AuditDto>
)