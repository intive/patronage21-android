package com.intive.calendar.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.repository.domain.model.User
import com.intive.ui.components.TitleText
import com.intive.ui.components.HeaderWithCount
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.intive.calendar.fragments.EventFragmentDirections
import com.intive.ui.components.PersonListItem
import com.intive.calendar.utils.*
import com.intive.shared.EventParcelable
import com.intive.ui.components.LayoutContainer



@Composable
fun EventScreenLayout(
    navController: NavController,
    updateInviteResponse: (Long, Long, String, () -> Unit) -> Unit,
    event: EventParcelable,
    refreshEventsList: () -> Unit
) {
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

                            val directions = EventFragmentDirections.actionEventFragmentToEditEventFragment(eventInfoParcelable = event)

                            IconButton(onClick = { navController.navigate(directions) }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = stringResource(R.string.edit_event_description),
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            IconButton(onClick = {}) {
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
                    count = event.users.size,
                    showCount = true,
                )

                UsersList(navController = navController, users = event.users)
            }

            Column {

                if (event.active) {
                    InviteResponseButtons(
                        event = event,
                        updateInviteResponse = updateInviteResponse,
                        refreshEventsList = refreshEventsList
                    )
                }
            }
        }
    }
}

@Composable
fun InviteResponseButtons(
    event: EventParcelable,
    updateInviteResponse: (Long, Long, String, () -> Unit) -> Unit,
    refreshEventsList: () -> Unit
) {

    val acceptBtnSelected = remember { mutableStateOf(false) }
    val unknownBtnSelected = remember { mutableStateOf(false) }
    val declineBtnSelected = remember { mutableStateOf(false) }

    when (event.inviteResponse) {
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

                if (event.inviteResponse != InviteResponse.ACCEPTED.name) {
                    updateInviteResponse(
                        userId,
                        event.id,
                        InviteResponse.ACCEPTED.name,
                        refreshEventsList,
                    )
                }

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
                if (event.inviteResponse != InviteResponse.UNKNOWN.name) {
                    updateInviteResponse(
                        userId,
                        event.id,
                        InviteResponse.UNKNOWN.name,
                        refreshEventsList
                    )
                }

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
                if (event.inviteResponse != InviteResponse.DECLINED.name) {
                    updateInviteResponse(
                        userId,
                        event.id,
                        InviteResponse.DECLINED.name,
                        refreshEventsList
                    )
                }

                acceptBtnSelected.value = false
                unknownBtnSelected.value = false
                declineBtnSelected.value = true
            }
        }
    }
}

@Composable
fun UsersList(navController: NavController, users: List<User>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState, modifier = Modifier.padding(bottom = 12.dp)) {
        items(users) { user ->
            PersonListItem(
                user = user,
                onItemClick = { navController.navigate(Uri.parse("intive://userDetails/${user.login}"))},
                rowPadding = 0.dp,
                showAdditionalText = true,
                additionalText = user.role
            )
        }
    }
}