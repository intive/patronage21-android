package com.intive.users.presentation.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.intive.ui.components.HeaderWithCount
import com.intive.users.presentation.details.DetailsViewModel
import com.intive.users.R
import com.intive.repository.domain.model.User
import com.intive.ui.components.LayoutContainer
import com.intive.ui.components.PrimaryButton
import com.intive.ui.components.SecondaryButton
import com.intive.ui.components.Divider
import com.intive.users.presentation.composables.ProjectListItem

@Composable
fun DetailsScreen(
    navController: NavController,
    user: User,
    viewModel: DetailsViewModel,
    projects: List<DetailsViewModel.Project>
) {
    val scrollState = rememberScrollState()

    LayoutContainer {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .height(70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
                        contentDescription = stringResource(id = R.string.profile_picture),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
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
                        .padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                )
            }

            Column {
                HeaderWithCount(
                    text = stringResource(R.string.projects),
                    count = projects.size,
                    showCount = true
                )
                projects.forEach { project ->
                    ProjectListItem(project)
                    Divider()
                }
            }

            Column {
                HeaderWithCount(text = stringResource(R.string.contact))

                Text(
                    user.email,
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                )

                ContactActionButton(stringResource(R.string.send_message)) {
                    viewModel.onSendEmailClicked(user.email)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Divider()

                Text(
                    user.phoneNumber,
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                )
                ContactActionButton(stringResource(R.string.call)) {
                    viewModel.onDialPhoneClicked(user.phoneNumber)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Divider()

                Text(
                    user.github,
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                )
                ContactActionButton(stringResource(R.string.open_link)) {
                    viewModel.onLaunchWebsiteClicked(user.github)
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 30.dp, bottom = 16.dp)
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
            .padding(start = 8.dp)
            .width(200.dp)
    ) {
        Text(
            text,
            color = Color.White
        )
    }
}