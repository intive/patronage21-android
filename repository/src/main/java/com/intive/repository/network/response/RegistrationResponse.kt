package com.intive.repository.network.response

import com.google.gson.annotations.SerializedName

class RegistrationResponse(
    @SerializedName("violationErrors")
    val violationErrors: List<RegistrationError>?) {
}

data class RegistrationError(
    @SerializedName("fieldName")
    val fieldName: String?,
    @SerializedName("rejectedValue")
    val rejectedValue: Any?,
    @SerializedName("message")
    val message: String?
)