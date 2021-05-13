package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.ui.components.HeaderWithCount
import com.intive.users.presentation.details.DetailsViewModel
import com.intive.users.R
import com.intive.repository.domain.model.User
import com.intive.ui.components.PrimaryButton
import com.intive.ui.components.SecondaryButton
import com.intive.users.presentation.composables.ProjectListItem

@Composable
fun DetailsScreen(
    navController: NavController,
    user: User,
    projects: List<DetailsViewModel.Project>
) {
    Column(
        Modifier
            .height(90.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "${user.firstName} ${user.lastName}",
                modifier = Modifier
                    .padding(start = 8.dp),
                fontSize = 22.sp,
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Column {
        HeaderWithCount(text = stringResource(R.string.bio))
        Text(
            text = user.bio,
            modifier = Modifier
                .padding(16.dp)
        )
    }

    Column {
        HeaderWithCount(text = stringResource(R.string.projects), count = projects.size, showCount = true)
        projects.forEach { project ->
            ProjectListItem(project)
            Divider(
                color = Color(0xFFF1F1F1),
                thickness = 2.dp
            )
        }
    }

    Column {
        HeaderWithCount(text = stringResource(R.string.contact))

        Text(user.email, modifier = Modifier.padding(16.dp))

        ContactActionButton(stringResource(R.string.send_message)) {

        }

        Divider(
            color = Color(0xFFF1F1F1),
            thickness = 2.dp,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        )

        Text(user.phoneNumber, modifier = Modifier.padding(16.dp))
        ContactActionButton(stringResource(R.string.call)) {

        }
        Divider(
            color = Color(0xFFF1F1F1),
            thickness = 2.dp,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        )

        Text(user.github, modifier = Modifier.padding(16.dp))
        ContactActionButton(stringResource(R.string.open_link)) {

        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 16.dp)
    ) {
        PrimaryButton(stringResource(R.string.edit_profile),
            onClick = {
                navController.navigate(R.id.action_detailsFragment_to_editUserFragment)
            }
        )
        Spacer(modifier = Modifier.size(10.dp))
        SecondaryButton(
            stringResource(R.string.deactivate_profile),
            onClick = {
                navController.navigate(R.id.action_detailsFragment_to_deactivateUserFragment)
            }
        )
    }
}

@Composable
fun ContactActionButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp)
            .width(200.dp)
    ) {
        Text(
            text,
            color = Color.White
        )
    }
}