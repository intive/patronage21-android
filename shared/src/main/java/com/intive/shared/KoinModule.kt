package com.intive.shared

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val navigationModule = module {
    viewModel { NavigationViewModel(get(), get()) }
}