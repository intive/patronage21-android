package com.intive.users.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.users.DeactivateUserViewModel
import com.intive.users.R

@Composable
fun DeactivateUserScreen(
    viewModel: DeactivateUserViewModel,
    navController: NavController
) {
    val query = mutableStateOf("")
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
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
            stringResource(R.string.deactivate_user_condition),
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.padding(8.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = query.value,
            onValueChange = {
                query.value = it
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

        Spacer(modifier = Modifier.padding(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    stringResource(R.string.deactivate_profile),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = {
                          navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(
                        start = 32.dp,
                        end = 32.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}