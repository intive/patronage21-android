package com.intive.repository.network.response

import com.intive.repository.network.model.UserDto

data class UsersResponse(
    val next_page: Int,
    val previous_page: Any,
    val size: Int,
    val users: List<UserDto>
)