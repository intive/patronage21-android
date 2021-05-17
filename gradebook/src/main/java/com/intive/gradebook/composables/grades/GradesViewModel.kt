package com.intive.gradebook.composables.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intive.repository.domain.model.GradebookUser

class GradesViewModel(
    val firstName: String,
    val lastName: String,
    val userName: String,
    val group: String,
    val gradeNames: Array<String>,
    val grades: FloatArray,
    val gradeReviews: Array<String>,
    val averageGrade: Float,
) : ViewModel() {
    var user = GradebookUser(
        firstName = firstName,
        lastName = lastName,
        userName = userName,
        group = group,
        gradeNames = gradeNames,
        grades = grades,
        gradeReviews = gradeReviews,
        averageGrade = averageGrade
    )
}

class GradesViewModelFactory(
    private val firstName: String,
    private val lastName: String,
    private val userName: String,
    private val group: String,
    private val gradeNames: Array<String>,
    private val grades: FloatArray,
    private val gradeReviews: Array<String>,
    private val averageGrade: Float,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GradesViewModel(firstName, lastName, userName, group, gradeNames, grades, gradeReviews, averageGrade) as T
    }
}