package com.intive.users.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.intive.users.R

@Composable
fun ImageEdit(
        onClick: () -> Unit
) {
    Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                    .padding(top = 15.dp)
                    .size(150.dp)

    ) {
        Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .clickable { onClick() }
        )
        Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(R.string.edit_icon)
        )
    }
}