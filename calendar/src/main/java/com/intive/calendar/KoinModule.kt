package com.intive.calendar

import com.intive.calendar.viewmodels.AddEditEventViewModel
import com.intive.calendar.viewmodels.CalendarHomeViewModel
import com.intive.calendar.viewmodels.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val calendarModule = module {
    viewModel { CalendarHomeViewModel(get(), get()) }
    viewModel { AddEditEventViewModel(get(), get(), get()) }
    viewModel { EventViewModel(get(), get(), get())}
}

