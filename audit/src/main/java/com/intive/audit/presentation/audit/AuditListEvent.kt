package com.intive.audit.presentation.audit

sealed class AuditListEvent {

    object NewSearchEvent: AuditListEvent()

    object NextPageEvent: AuditListEvent()
}