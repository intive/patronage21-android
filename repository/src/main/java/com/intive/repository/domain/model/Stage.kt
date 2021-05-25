package com.intive.repository.domain.model

data class Stage(
    val id: String,
    var name: String,
    var dateBegin: String,
    var dateEnd: String,
    var state: String
)