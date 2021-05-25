package com.intive.registration.viewmodels

import androidx.lifecycle.ViewModel
import com.intive.registration.viewmodels.RegistrationSuccessDialogState.HIDE_DIALOG

class SharedViewModel : ViewModel() {
    var successDialogState = HIDE_DIALOG
    var login: String = ""
}

object RegistrationSuccessDialogState {
    const val SHOW_DIALOG = "show"
    const val HIDE_DIALOG = "hide"
}