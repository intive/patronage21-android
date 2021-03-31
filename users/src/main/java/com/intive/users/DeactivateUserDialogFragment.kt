package com.intive.users

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels

class DeactivateUserDialogFragment : DialogFragment() {
    private val viewModel: DeactivateUserViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Czy na pewno chcesz dezaktywować użytkownika?")
            .setMessage("Operacji nie będzie można cofnąć")
            .setNegativeButton("Anuluj", null)
            .setPositiveButton("Deaktywuj") { _, _ ->
                viewModel.onConfirmClick()
            }
            .create()
}