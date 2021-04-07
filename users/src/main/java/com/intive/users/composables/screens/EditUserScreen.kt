package com.intive.users.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.users.DetailsViewModel
import com.intive.users.Person
import com.intive.users.R
import com.intive.users.composables.GroupsSpinner
import com.intive.users.edit_user.EditUserViewModel

@Composable
fun EditUserScreen(
    navController: NavController,
    user: Person,
    viewModel: EditUserViewModel,
) {
    val firstName = mutableStateOf(user.firstName)
    val lastName = mutableStateOf(user.lastName)
    val email = mutableStateOf(user.email)
    val phoneNumber = mutableStateOf(user.phoneNumber)
    val github = mutableStateOf(user.github)
    val bio = mutableStateOf(user.bio)

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 20.dp, end = 20.dp),
       // verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 16.dp)
                .width(150.dp)
                .height(150.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row {
            GroupsSpinner(
                groups = listOf("Mężczyzna", "Kobieta", "Inna")
            ) {

            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstName.value,
            onValueChange = {
                firstName.value = it
                user.firstName = it
            },
            label = { Text(text = stringResource(R.string.first_name)) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastName.value,
            onValueChange = {
                lastName.value = it
                user.lastName = it
            },
            label = { Text(text = stringResource(R.string.last_name)) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email.value,
            onValueChange = {
                email.value = it
                user.email = it
            },
            label = { Text(text = stringResource(R.string.email_address)) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumber.value,
            onValueChange = {
                phoneNumber.value = it
                user.phoneNumber = it
            },
            label = { Text(text = stringResource(R.string.phone_number)) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = github.value,
            onValueChange = {
                github.value = it
                user.github = it
            },
            label = { Text(text = stringResource(R.string.github)) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = bio.value,
            onValueChange = {
                bio.value = it
                user.bio = it
            },
            label = { Text(text = stringResource(R.string.bio)) }
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                //TODO: save changes here
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.save),
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = {
                //TODO: cancel changes here
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
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