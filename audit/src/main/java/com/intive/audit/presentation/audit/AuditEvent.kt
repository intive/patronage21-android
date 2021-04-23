package com.intive.audit.presentation.audit

sealed class AuditEvent {

    object NewSearchEvent: AuditEvent()

    object NextPageEvent: AuditEvent()
}