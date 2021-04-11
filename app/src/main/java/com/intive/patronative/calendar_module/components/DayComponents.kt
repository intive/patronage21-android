package com.intive.patronative.calendar_module.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.patronative.R
import com.intive.patronative.calendar_module.viewmodels.CalendarHomeViewModel


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
            EventsList(eventsList, navController)
        }

        Column {
            CancelButton(stringResource(R.string.go_back)) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun EventsList(eventsList: List<CalendarHomeViewModel.Event>, navController: NavController) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(eventsList.size) {
            EventsListItem(eventsList[it], navController)
        }
    }
}

@Composable
fun EventsListItem(event: CalendarHomeViewModel.Event, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate(R.id.action_dayFragment_to_eventFragment) })
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
        ) {

            Text(
                "${event.name}",
                style = MaterialTheme.typography.h6
            )
            Text(
                "Godzina: ${event.time}",
                style = MaterialTheme.typography.body1
            )

        }
    }

    Divider(color = Color.LightGray)
}
