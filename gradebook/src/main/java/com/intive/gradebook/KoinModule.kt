package com.intive.gradebook

import com.intive.gradebook.composables.gradebook.GradebookViewModel
import com.intive.gradebook.composables.grades.GradesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gradebookModule = module {
    viewModel { GradebookViewModel(get(), get()) }
}