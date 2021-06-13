package com.intive.repository.network.response

import com.google.gson.annotations.SerializedName

class RegistrationResponse(
    @SerializedName("violationErrors")
    val violationErrors: List<RegistrationError>) {
}

data class RegistrationError(
    @SerializedName("fieldName")
    val fieldName: String,
    @SerializedName("rejectedValue")
    val rejectedValue: String,
    @SerializedName("message")
    val message: String
)