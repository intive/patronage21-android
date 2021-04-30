package com.intive.repository.domain.model

data class Group(
    var name: String,
    var description: String,
    var technologies: List<String>
)