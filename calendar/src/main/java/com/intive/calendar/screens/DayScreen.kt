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
import com.intive.calendar.R
import com.intive.calendar.fragments.DayFragmentDirections
import com.intive.calendar.utils.DayParcelable
import com.intive.repository.domain.model.Event
import com.intive.shared.getHour
import com.intive.shared.EventParcelable
import com.intive.ui.components.Divider
import com.intive.ui.components.LayoutContainer
import com.intive.ui.components.TitleText


@Composable
fun DayLayout(
    navController: NavController,
    day: DayParcelable
) {
    LayoutContainer {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            TitleText(text = day.date, modifier = Modifier.padding(bottom = 16.dp))
            EventsList(
                date = day.date,
                eventsList = day.events,
                navController = navController,
                isDayActive = day.active
            )
        }
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

    val eventParcelable = EventParcelable(
        id = event._id,
        date = date,
        time = "${event.startDate.getHour()} - ${event.endDate.getHour()}",
        name = event.title,
        active = isDayActive
    )

    val directions =
        DayFragmentDirections.actionDayFragmentToEventFragment(eventInfoParcelable = eventParcelable)

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
                .padding(top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                event.title,
                style = typography.h6
            )
            Text(
                "${stringResource(R.string.hour)}: ${event.startDate.getHour()} - ${event.endDate.getHour()}",
                style = typography.body1
            )

        }
    }

    Divider()
}
