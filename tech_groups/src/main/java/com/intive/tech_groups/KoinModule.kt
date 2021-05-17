package com.intive.tech_groups

import com.intive.tech_groups.presentation.viewmodels.StageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val techGroupsModule = module {
    viewModel { StageViewModel(get(), get()) }
}
