package com.intive.calendar.screens

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
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.google.gson.Gson
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.repository.domain.model.Event
import com.intive.ui.components.TitleText


@Composable
fun DayLayout(
    navController: NavController,
    date: String,
    eventsList: List<Event>,
    refreshCalendar: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TitleText(date, Modifier.padding(bottom = 24.dp))
            EventsList(eventsList, date, navController)
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
    date: String,
    navController: NavController
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(eventsList.size) {
            EventsListItem(eventsList[it], date, navController)
        }
    }
}

@Composable
fun EventsListItem(event: Event, date: String, navController: NavController) {

    val bundle = bundleOf(
        "date" to date,
        "time" to "${event.timeStart} - ${event.timeEnd}",
        "name" to event.name,
        "users" to Gson().toJson(event.users)
    )

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
