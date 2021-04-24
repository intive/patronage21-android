package com.intive.calendar

import com.intive.calendar.viewmodels.CalendarHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val calendarModule = module {
    viewModel { CalendarHomeViewModel(get()) }
}

