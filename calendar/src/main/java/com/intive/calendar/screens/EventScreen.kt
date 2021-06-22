package com.intive.calendar.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R
import com.intive.repository.domain.model.User
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.intive.calendar.components.ResponseButton
import com.intive.calendar.fragments.EventFragmentDirections
import com.intive.calendar.utils.InviteResponse
import com.intive.calendar.viewmodels.EventViewModel
import com.intive.repository.domain.ListUserJava
import com.intive.shared.EventParcelable
import com.intive.ui.components.*


@Composable
fun EventScreenLayout(
    eventViewModel: EventViewModel,
    navController: NavController,
    event: EventParcelable,
    refreshEventsList: () -> Unit
) {

    val showDeleteDialog by eventViewModel.showDeleteDialog.observeAsState()
    val usersList by eventViewModel.usersList.observeAsState()

    if (showDeleteDialog == true) {
        DeleteEventDialog(
            viewModel = eventViewModel,
            navController = navController,
            refreshEventsList = refreshEventsList,
            showDeleteDialog = showDeleteDialog,
            eventId = event.id
        )
    }

    LayoutContainer {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Column(modifier = Modifier.weight(1f)) {

                TitleText(text = event.date, modifier = Modifier.padding(bottom = 24.dp))


                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(2f)) {
                        TitleText(
                            text = event.name,
                            modifier = Modifier.padding(bottom = 4.dp),
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )

                        Text(
                            "${stringResource(R.string.hour)}: ${event.time}",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            val directions =
                                EventFragmentDirections.actionEventFragmentToEditEventFragment(
                                    eventInfoParcelable = event
                                )

                            IconButton(onClick = { navController.navigate(directions) }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = stringResource(R.string.edit_event_description),
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            IconButton(onClick = { eventViewModel.showDeleteDialog(true) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete_event_description),
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }

                HeaderWithCount(
                    text = stringResource(R.string.event_users_label),
                    count = if (usersList == null) 0 else usersList?.size,
                    showCount = true,
                )

                if (usersList != null) {
                    UsersList(navController = navController, users = usersList!!)
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

            }

            Column {

                if (event.active) {
                    InviteResponseButtons()
                }
            }
        }
    }
}


@Composable
fun InviteResponseButtons() {

    val inviteResponse = InviteResponse.values().random()
    val acceptBtnSelected = remember { mutableStateOf(false) }
    val unknownBtnSelected = remember { mutableStateOf(false) }
    val declineBtnSelected = remember { mutableStateOf(false) }

    when (inviteResponse.name) {
        InviteResponse.ACCEPTED.name -> acceptBtnSelected.value = true
        InviteResponse.UNKNOWN.name -> unknownBtnSelected.value = true
        InviteResponse.DECLINED.name -> declineBtnSelected.value = true
    }

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
            ResponseButton(
                text = stringResource(id = R.string.accept_invite_btn_label),
                onSelectedColor = colorResource(id = R.color.dark_green),
                selected = acceptBtnSelected
            )
            {
                acceptBtnSelected.value = true
                unknownBtnSelected.value = false
                declineBtnSelected.value = false
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            ResponseButton(
                text = stringResource(id = R.string.unknown_invite_btn_label),
                onSelectedColor = colorResource(id = R.color.dark_gray),
                selected = unknownBtnSelected
            )
            {
                acceptBtnSelected.value = false
                unknownBtnSelected.value = true
                declineBtnSelected.value = false
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            ResponseButton(
                text = stringResource(id = R.string.decline_invite_btn_label),
                onSelectedColor = colorResource(id = R.color.dark_red),
                selected = declineBtnSelected
            )
            {
                acceptBtnSelected.value = false
                unknownBtnSelected.value = false
                declineBtnSelected.value = true
            }
        }
    }
}


@Composable
fun UsersList(navController: NavController, users: List<ListUserJava>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState, modifier = Modifier.padding(bottom = 12.dp)) {
        items(users) { user ->
            PersonListItem(
                user = User(
                    login = user.login,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    gender = "",
                    image = null,
                    projects = emptyList(),
                    email = "",
                    phoneNumber = "",
                    github = "",
                    bio = "",
                    role = ""
                ),
                onItemClick = { navController.navigate(Uri.parse("intive://userDetails/${user.login}")) },
                rowPadding = 0.dp,
                showAdditionalText = false
            )
        }
    }
}

@Composable
fun DeleteEventDialog(
    viewModel: EventViewModel,
    navController: NavController,
    refreshEventsList: () -> Unit,
    showDeleteDialog: Boolean?,
    eventId: String
) {
    Column {

        if (showDeleteDialog == true) {

            val navigationFun =
                if (navController.previousBackStackEntry?.destination?.id == R.id.calendarFragment || navController.previousBackStackEntry?.destination?.id == R.id.dayFragment) {
                    { navController.popBackStack(R.id.calendarFragment, false) }
                } else {
                    { navController.popBackStack()
                    }
                }

            AlertDialog(
                onDismissRequest = { viewModel.showDeleteDialog(false) },
                title = {
                    Text(
                        stringResource(R.string.delete_event_dialog_text),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                },
                buttons = {
                    Column(modifier = Modifier.padding(12.dp)) {
                        PrimaryButton(
                            text = stringResource(R.string.ok),
                            paddingBottom = 8.dp
                        ) {
                            viewModel.deleteEvent(
                                eventId,
                                { navigationFun() }
                            ) { refreshEventsList() }
                            viewModel.showDeleteDialog(false)
                        }
                        SecondaryButton(text = stringResource(R.string.cancel_dialog)) {
                            viewModel.showDeleteDialog(false)
                        }
                    }
                },
            )
        }
    }
}