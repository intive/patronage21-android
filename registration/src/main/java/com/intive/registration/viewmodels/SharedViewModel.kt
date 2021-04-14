package com.intive.registration.viewmodels

import androidx.lifecycle.ViewModel
import com.intive.registration.viewmodels.RegistrationSuccessDialog.HIDE_DIALOG

class SharedViewModel : ViewModel() {
    var successDialog = HIDE_DIALOG
}

object RegistrationSuccessDialog {
    const val SHOW_DIALOG = "show"
    const val HIDE_DIALOG = "hide"
}