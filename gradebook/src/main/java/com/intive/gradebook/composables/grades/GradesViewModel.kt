package com.intive.gradebook.composables.grades

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intive.gradebook.composables.Grades
import com.intive.gradebook.composables.gradebook.GradebookViewModel
import com.intive.repository.domain.model.Gradebook

class GradesViewModel(
    val firstName: String,
    val lastName: String,
    val gradeNames: Array<String>,
    val grades: FloatArray,
    val gradeReviews: Array<String>,
    val averageGrade: Float,
) : ViewModel() {
    var user = Grades(
        firstName = firstName,
        lastName = lastName,
        gradeNames = gradeNames,
        grades = grades,
        gradeReviews = gradeReviews,
        averageGrade = averageGrade
    )
}

class GradesViewModelFactory(
    private val firstName: String,
    private val lastName: String,
    private val gradeNames: Array<String>,
    private val grades: FloatArray,
    private val gradeReviews: Array<String>,
    private val averageGrade: Float,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GradesViewModel(firstName, lastName, gradeNames, grades, gradeReviews, averageGrade) as T
    }
}