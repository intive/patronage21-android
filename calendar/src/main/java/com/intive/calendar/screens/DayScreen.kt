package com.intive.calendar.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.fragments.DayFragmentDirections
import com.intive.calendar.utils.DayBundle
import com.intive.repository.domain.model.Event
import com.intive.shared.EventBundle
import com.intive.ui.components.Divider
import com.intive.ui.components.TitleText


@Composable
fun DayLayout(
    navController: NavController,
    day: DayBundle
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        TitleText(text = day.date, modifier = Modifier.padding(bottom = 24.dp))
        EventsList(
            date = day.date,
            eventsList = day.events,
            navController = navController,
            isDayActive = day.active
        )
    }
}

@Composable
fun EventsList(
    date: String,
    eventsList: List<Event>,
    navController: NavController,
    isDayActive: Boolean
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(eventsList.size) {
            EventsListItem(
                date = date,
                event = eventsList[it],
                navController = navController,
                isDayActive = isDayActive
            )
        }
    }
}

@Composable
fun EventsListItem(date: String, event: Event, navController: NavController, isDayActive: Boolean) {

    val eventBundle = EventBundle(
        id = event.id,
        date = date,
        time = "${event.timeStart} - ${event.timeEnd}",
        name = event.name,
        inviteResponse = event.inviteResponse,
        users = event.users,
        active = isDayActive
    )

    val eventSerialized = Gson().toJson(eventBundle)
    val directions = DayFragmentDirections.actionDayFragmentToEventFragment(eventSerialized)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(
                    directions
                )
            })
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 8.dp)
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

    Divider()
}
