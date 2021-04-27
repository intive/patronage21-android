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
import com.intive.ui.components.UsersHeader
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import com.intive.calendar.utils.EventBundle
import com.intive.calendar.utils.InviteResponse
import com.intive.ui.components.PersonListItem


@Composable
fun EventScreenLayout(
    navController: NavController,
    event: EventBundle,
    refreshCalendar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            TitleText(event.date, Modifier.padding(bottom = 24.dp))
            TitleText(
                event.name,
                Modifier.padding(bottom = 4.dp),
                MaterialTheme.typography.h6,
                Color.Black
            )

            Text(
                "${stringResource(R.string.hour)}: ${event.time}",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            UsersHeader(
                text = stringResource(R.string.event_users_label),
                count = event.users.size,
                showCount = true,
            )

            UsersList(event.users)
        }

        Column {
            InviteResponseButtons(event)
            CancelButton(stringResource(R.string.go_back)) {
                refreshCalendar()
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun InviteResponseButtons(event: EventBundle) {

    val acceptBtnSelected = remember { mutableStateOf(false) }
    val unknownBtnSelected = remember { mutableStateOf(false) }
    val declineBtnSelected = remember { mutableStateOf(false) }

    when (event.invite) {
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
                // TODO: send decision to API
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
                // TODO: send decision to API
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
                // TODO: send decision to API
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
            PersonListItem(user, {}, 0.dp, true)
        }
    }
}