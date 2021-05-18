package com.intive.repository.domain.model

data class Gradebook(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val group: String,
    val entries: Array<Grades>,
    val averageGrade: Float
)
