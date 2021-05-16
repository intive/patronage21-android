package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.ui.components.LayoutContainer
import com.intive.ui.components.PrimaryButton
import com.intive.ui.components.SecondaryButton
import com.intive.users.R
import com.intive.users.presentation.deactivate_user.DeactivateUserViewModel

@Composable
fun DeactivateUserScreen(
    viewModel: DeactivateUserViewModel,
    navController: NavController
) {
    val lastName = viewModel.lastName
    LayoutContainer {
        Text(
            stringResource(R.string.deactivate_user_question),
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            stringResource(R.string.deactivate_user_warning),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            stringResource(R.string.deactivate_user_condition)
        )
        Spacer(modifier = Modifier.padding(8.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = lastName.value,
            onValueChange = {
                viewModel.onValueChange(it)
            },
            label = { Text(text = stringResource(R.string.last_name)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onNext = {

                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                focusedLabelColor = MaterialTheme.colors.secondaryVariant,
                focusedIndicatorColor = MaterialTheme.colors.secondaryVariant,
                cursorColor = MaterialTheme.colors.secondaryVariant,
            ),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 30.dp)
        ) {
            PrimaryButton(
                text = stringResource(R.string.deactivate_profile),
                enabled = viewModel.isLastNameCorrect(),
                onClick = {
                    viewModel.onConfirmClick()
                }
            )
            Spacer(modifier = Modifier.size(10.dp))
            SecondaryButton(
                text = stringResource(R.string.cancel),
                onClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}