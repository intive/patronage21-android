package com.intive.registration

import com.intive.registration.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val registrationModule = module {
    viewModel { RegistrationViewModel(get()) }
}