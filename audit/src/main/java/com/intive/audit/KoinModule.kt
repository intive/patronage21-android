package com.intive.audit

import androidx.lifecycle.viewmodel.compose.viewModel
import com.intive.audit.presentation.audit.AuditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val auditModule = module {
    viewModel{ AuditViewModel(get(), get()) }
}