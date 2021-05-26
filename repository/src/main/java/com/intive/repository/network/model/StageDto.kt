package com.intive.repository.network.model

data class StageDto (
    val id: String,
    var name: String,
    var dateBegin: String,
    var dateEnd: String,
    var state: String
)