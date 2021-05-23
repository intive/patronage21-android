package com.intive.tech_groups.presentation.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.viewmodels.StageViewModel
import com.intive.ui.components.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.intive.repository.domain.model.Event
import com.intive.shared.EventParcelable
import com.intive.shared.getFullDateString
import com.intive.shared.stringToCalendar
import java.util.*


@Composable
fun StageScreen(
    stageViewModel: StageViewModel,
    navController: NavController
) {

    val stageDetails by stageViewModel.stageDetails.observeAsState()


    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(scrollState),
    ) {

        LayoutContainer {

            if (stageDetails != null) {

                TitleText(
                    text = "${stageDetails!!.name}",
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                HeaderWithCount(text = stringResource(R.string.task_description))
                Paragraph(
                    "${stageDetails!!.description}",
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )

                Row(modifier = Modifier.padding(bottom = 24.dp)) {

                    val isEnabled = stageDetails!!.areMaterialsAvailable

                    PrimaryButton(
                        text = stringResource(R.string.download_resources_btn),
                        onClick = { /*TODO*/ },
                        enabled = isEnabled!!
                    )
                }

                HeaderWithCount(
                    text = stringResource(R.string.meetings_schedule),
                    count = stageDetails!!.events.size,
                    showCount = true
                )

                stageDetails!!.events.forEach { event ->
                    EventListItem(
                        event = event,
                        navController = navController
                    )
                    Divider(color = Color.LightGray)
                }

                Spacer(Modifier.height(24.dp))
                HeaderWithCount(text = stringResource(R.string.stage_completion))

                Column(modifier = Modifier.padding(top = 8.dp)) {

                    val isStageCompletedString =
                        if (stageDetails!!.isStageCompleted == true) stringResource(R.string.stage_completed) else stringResource(
                            R.string.stage_not_completed
                        )

                    Text(
                        isStageCompletedString,
                        style = MaterialTheme.typography.subtitle1
                    )

                    Row {
                        Text(
                            stringResource(R.string.completion_level_percentage),
                            style = MaterialTheme.typography.body1
                        )
                        Text("${stageDetails!!.completionLevel}")
                    }
                }

                Row(modifier = Modifier.padding(top = 24.dp)) {
                    PrimaryButton(
                        text = stringResource(R.string.check_gradebook_btn),
                        onClick = { /*TODO*/ })
                }


            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}


@Composable
fun EventListItem(
    event: Event,
    navController: NavController
) {

    var rowModifier: Modifier = Modifier.fillMaxWidth()
    var fontColor: Color = Color.Black

    val eventParcelable = EventParcelable(
        id = event.id,
        date = event.date,
        time = "${event.timeStart} - ${event.timeEnd}",
        name = event.name,
        inviteResponse = event.inviteResponse,
        users = event.users,
        active = true /*TODO*/
    )

    val eventSerialized = Gson().toJson(eventParcelable)

    if (stringToCalendar(dateString = event.date, timeEnd = event.timeEnd).before(Calendar.getInstance())) {
        fontColor = Color.Gray
    } else {
        rowModifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate(Uri.parse("intive://eventFragment/$eventSerialized")) })
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = rowModifier
    ) {
        Spacer(Modifier.width(10.dp))

        Column {

            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(
                    text = getFullDateString(event.date),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = fontColor
                    )
                )
            }

            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                Text(
                    text = "${event.name}, ${event.timeStart}-${event.timeEnd}",
                    fontSize = 18.sp,
                    color = fontColor
                )
            }
        }
    }
}