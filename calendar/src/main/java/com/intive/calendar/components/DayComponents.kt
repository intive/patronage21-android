package com.intive.calendar.components

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
import com.intive.calendar.R
import com.intive.calendar.viewmodels.CalendarHomeViewModel


@Composable
fun DayLayout(
    navController: NavController,
    date: String,
    eventsList: List<CalendarHomeViewModel.Event>
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            HeaderLarge(date)
            EventsList(eventsList, date, navController)
        }

        Column {
            CancelButton(stringResource(R.string.go_back)) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun EventsList(eventsList: List<CalendarHomeViewModel.Event>, date: String, navController: NavController) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(eventsList.size) {
            EventsListItem(eventsList[it], date, navController)
        }
    }
}

@Composable
fun EventsListItem(event: CalendarHomeViewModel.Event, date: String, navController: NavController) {

    val bundle = bundleOf("date" to date, "time" to event.time, "name" to event.name)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate(R.id.action_dayFragment_to_eventFragment, bundle) })
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
                "${stringResource(R.string.hour)}: ${event.time}",
                style = typography.body1
            )

        }
    }

    Divider(color = Color.LightGray)
}
