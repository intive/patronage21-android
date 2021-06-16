package com.intive.ui.components

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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intive.repository.domain.model.User
import com.intive.shared.decodeBase64IntoBitmap
import com.intive.ui.R


@Composable
fun PersonListItem(
    modifier: Modifier = Modifier,
    user: User,
    onItemClick: (User) -> Unit,
    rowPadding: Dp = 16.dp,
    additionalText: String = "",
    showAdditionalText: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(
                start = rowPadding,
                end = rowPadding
            )
            .clickable {
                onItemClick(user)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    bitmap = if(user.image != null) {
                        user.image!!.decodeBase64IntoBitmap().asImageBitmap()
                    } else {
                        ImageBitmap.imageResource(id = R.drawable.blank_user)
                    },
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

        if (showAdditionalText) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = additionalText, modifier = modifier)
            }
        }
    }
}
