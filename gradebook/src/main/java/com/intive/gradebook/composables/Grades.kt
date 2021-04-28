package com.intive.gradebook.composables

data class Grades(
    val firstName: String,
    val lastName: String,
    val gradeNames: Array<String>,
    val grades: FloatArray,
    val gradeReviews: Array<String>,
    val averageGrade: Float
)