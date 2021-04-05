package com.example.patron_a_tive.calendar_module.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.patron_a_tive.R

@Composable
fun DayFragmentLayout(onClickOKBtn: () -> Unit, onClickCancelBtn: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            HeaderLarge("Piątek, 05.03.2021")
            HeaderMedium("Retrospective", Modifier.padding(bottom = 4.dp))

            Text(
                "Godzina: 15:00-16:00",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.pale_blue))
                    .padding(12.dp)
            ) {
                Text(
                    stringResource(R.string.event_users_label),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondary
                )

                Text(
                    "10",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondary
                )
            }
            UsersList()
        }

        Column {
            // TODO: modify onClick handlers
            OKButton(stringResource(R.string.accept_event), onClickOKBtn)
            CancelButton(stringResource(R.string.reject_event), onClickCancelBtn)
        }
    }
}

@Composable
fun UsersList() {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(10) {
            UsersListItem(it)
        }
    }
}

@Composable
fun UsersListItem(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.header),
            contentDescription = "User's profile pic",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(30.dp)
                .height(30.dp)
                .clip(CircleShape)
        )

        Text(
            "Uczestnik ${index + 1}",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 16.dp)
        )
        //Text("Organizator", style = MaterialTheme.typography.subtitle1)
    }
    Divider(color = Color.LightGray)
}