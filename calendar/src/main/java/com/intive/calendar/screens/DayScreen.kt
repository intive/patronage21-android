package com.intive.calendar.screens

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.calendar.utils.DayBundle
import com.intive.calendar.utils.EventBundle
import com.intive.repository.domain.model.Event
import com.intive.ui.components.TitleText


@Composable
fun DayLayout(
    navController: NavController,
    day: DayBundle,
    refreshCalendar: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TitleText(day.date, Modifier.padding(bottom = 24.dp))
            EventsList(day.events, navController, day.active)
        }

        Column {
            CancelButton(stringResource(R.string.go_back)) {
                refreshCalendar()
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun EventsList(
    eventsList: List<Event>,
    navController: NavController,
    isDayActive: Boolean
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(eventsList.size) {
            EventsListItem(eventsList[it], navController, isDayActive)
        }
    }
}

@Composable
fun EventsListItem(event: Event, navController: NavController, isDayActive: Boolean) {

    val eventBundle = EventBundle(
        id = event.id,
        date = event.date,
        time = "${event.timeStart} - ${event.timeEnd}",
        name = event.name,
        inviteResponse = event.inviteResponse,
        users = event.users,
        active = isDayActive
    )
    val bundle = Bundle()
    bundle.putParcelable("event", eventBundle)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(
                    R.id.action_dayFragment_to_eventFragment,
                    bundle
                )
            })
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {

            Text(
                event.name,
                style = typography.h6
            )
            Text(
                "${stringResource(R.string.hour)}: ${event.timeStart} - ${event.timeEnd}",
                style = typography.body1
            )

        }
    }

    Divider(color = Color.LightGray)
}
