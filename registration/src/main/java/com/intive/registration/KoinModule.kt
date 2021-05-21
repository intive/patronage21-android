package com.intive.registration

import com.intive.registration.viewmodels.EmailVerificationViewModel
import com.intive.registration.viewmodels.LoginViewModel
import com.intive.registration.viewmodels.NoCodeViewModel
import com.intive.registration.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val registrationModule = module {
    viewModel { RegistrationViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { EmailVerificationViewModel(get(), get()) }
    viewModel { NoCodeViewModel(get(), get()) }
}