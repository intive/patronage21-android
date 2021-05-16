package com.intive.calendar

import com.intive.calendar.viewmodels.AddEventViewModel
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.calendar.viewmodels.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val calendarModule = module {
    viewModel { CalendarHomeViewModel(get(), get()) }
    viewModel { AddEventViewModel(get(), get()) }
    viewModel { EventViewModel(get(), get())}
}

