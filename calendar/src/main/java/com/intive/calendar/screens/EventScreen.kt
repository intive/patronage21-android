package com.intive.calendar.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.calendar.R
import com.intive.calendar.components.*
import com.intive.ui.components.TitleText
import com.intive.ui.components.UsersHeader


@Composable
fun EventFragmentLayout(navController: NavController, date: String, time: String, name: String) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            TitleText(date, Modifier.padding(bottom = 24.dp))
            TitleText(
                name,
                Modifier.padding(bottom = 4.dp),
                MaterialTheme.typography.h6,
                Color.Black
            )

            Text(
                "${stringResource(R.string.hour)}: $time",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            UsersHeader(
                text = stringResource(R.string.event_users_label),
                count = 10,
                showCount = true,
            )

            UsersList()
        }

        Column {
            OKButton(stringResource(R.string.accept_event)) { navController.popBackStack() }
            CancelButton(stringResource(R.string.reject_event)) { navController.popBackStack() }
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
            contentDescription = stringResource(R.string.user_image_desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(30.dp)
                .height(30.dp)
                .clip(CircleShape)
        )

        Text(
            "${stringResource(R.string.event_user)} ${index + 1}",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    Divider(color = Color.LightGray)
}