package com.intive.users.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intive.repository.domain.model.User

@Composable
fun UserListItem(
    user: User,
    onItemClick: (User) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .clickable {
                   onItemClick(user)
            },
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = com.intive.users.R.drawable.aaa),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 16.dp)
                .width(30.dp)
                .height(30.dp)
                .clip(CircleShape)
        )
        Text(
            text = "${user.firstName} ${user.lastName}",
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 12.dp)
        )
    }
}

