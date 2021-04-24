package com.intive.registration.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.registration.Constants
import com.intive.registration.R
import com.intive.registration.components.CustomButton
import com.intive.registration.fragments.ErrorFragmentDirections
import com.intive.ui.components.TitleText

@Composable
fun ErrorScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(text = stringResource(R.string.error_title), modifier = Modifier)
        Spacer(modifier = Modifier.height(Constants.SPACER_HEIGHT))
        CustomButton(
            text = stringResource(R.string.error_home_button),
            onClick = {
                val action = ErrorFragmentDirections.actionBackToLogin()
                navController.navigate(action)
            }
        )
    }
}
