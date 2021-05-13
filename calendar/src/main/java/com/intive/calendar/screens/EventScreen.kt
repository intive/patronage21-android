package com.intive.calendar.screens

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
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.repository.domain.model.User
import com.intive.ui.components.TitleText
import com.intive.ui.components.HeaderWithCount
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import com.intive.calendar.utils.EventBundle
import com.intive.ui.components.PersonListItem
import com.intive.calendar.utils.*


@Composable
fun EventScreenLayout(
    updateInviteResponse: (Long, Long, String, () -> Unit) -> Unit,
    navController: NavController,
    event: EventBundle,
    refreshCalendar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            TitleText(text = event.date, modifier = Modifier.padding(bottom = 24.dp))
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

            HeaderWithCount(
                text = stringResource(R.string.event_users_label),
                count = event.users.size,
                showCount = true,
            )

            UsersList(users = event.users)
        }

        Column {

            if (event.active) {
                InviteResponseButtons(
                    event = event,
                    updateInviteResponse = updateInviteResponse,
                    refreshCalendar = refreshCalendar
                )
            }

            CancelButton(stringResource(R.string.go_back)) {
                refreshCalendar()
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun InviteResponseButtons(
    event: EventBundle,
    updateInviteResponse: (Long, Long, String, () -> Unit) -> Unit,
    refreshCalendar: () -> Unit
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
                        refreshCalendar,
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
                        refreshCalendar
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
                        refreshCalendar
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
fun UsersList(users: List<User>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState, modifier = Modifier.padding(bottom = 12.dp)) {
        items(users) { user ->
            PersonListItem(
                user = user,
                onItemClick =  {},
                rowPadding =  0.dp,
                showAdditionalText = true,
                additionalText = user.role
            )
        }
    }
}