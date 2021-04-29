package com.intive.repository.network.response

import com.google.gson.annotations.SerializedName
import com.intive.repository.network.model.UserDto

data class UsersResponse(
    @SerializedName("next_page")
    val nextPage: Int?,
    @SerializedName("previous_page")
    val previousPage: Int?,
    val size: Int,
    @SerializedName("total_size")
    val totalSize: Int,
    val users: List<UserDto>
)