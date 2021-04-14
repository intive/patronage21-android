package com.intive.gradebook.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.gradebook.AddColumnViewModel
import com.intive.gradebook.R
import com.intive.gradebook.composables.ColumnSpinner
import com.intive.gradebook.composables.GroupsSpinner

@Composable
fun AddColumnScreen(
    viewModel: AddColumnViewModel,
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    )
    {
        Text(
            text = stringResource(id = R.string.select_data),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(16.dp))
        ColumnSpinner(
            columns = listOf(
                "Grupa",
                "Ostatnie ocena",
                "Ocena za etap I",
                "Ocena za etap II",
                "Ocena za etap III",
                "Ocena za etap IV",
                "Ocena za etap V",
            )) {

        }
        Row {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC2185A)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(R.string.save_data),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFC2185A)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(16.dp)
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
