package com.intive.repository.util

const val RESPONSE_OK = 200
const val RESPONSE_NOT_FOUND = 404

fun isServerError(code: Int): Boolean = code in 500..599
const val SERVER_ERROR = "server_error"