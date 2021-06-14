package com.intive.users

import com.intive.users.presentation.user.UserViewModel
import com.intive.users.presentation.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val usersModule = module {
    viewModel { UsersViewModel(get(), get()) }
    viewModel { UserViewModel(get(), get(), get(), get()) }
}