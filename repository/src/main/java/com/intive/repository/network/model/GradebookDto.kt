package com.intive.repository.network.model

import com.intive.repository.domain.model.Grades

data class GradebookDto(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val group: String,
    val entries: Array<Grades>,
    val averageGrade: Float
)
