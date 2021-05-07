package com.intive.tech_groups

import com.intive.tech_groups.presentation.viewmodels.AddGroupViewModel
import com.intive.tech_groups.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val technologyGroupsModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AddGroupViewModel(get()) }
}