package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.R
import com.intive.registration.fragments.ErrorFragmentDirections
import com.intive.ui.components.IntroSection
import com.intive.ui.components.PrimaryButton

@Composable
fun ErrorScreen(navController: NavController, messageResourceId: Int) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        IntroSection(
            title = stringResource(R.string.error_occured),
            text = stringResource(id = messageResourceId)
        )
        PrimaryButton(
            text = stringResource(R.string.error_home_button)
        ) {
            val action = ErrorFragmentDirections.actionBackToLogin()
            navController.navigate(action)
        }
    }
}
