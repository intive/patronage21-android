package com.intive.repository.domain.model

data class GradebookUser(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val group: String,
    val gradeNames: Array<String>,
    val grades: FloatArray,
    val gradeReviews: Array<String>,
    val averageGrade: Float
)
