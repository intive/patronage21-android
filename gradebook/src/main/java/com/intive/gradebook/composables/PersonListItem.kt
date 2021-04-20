package com.intive.gradebook.composables

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intive.gradebook.domain.Person
import com.intive.gradebook.domain.Grade
import com.intive.gradebook.R

@Composable
fun PersonListItem(
    person: Person,
    addedColumn: String = "",
    onItemClick: (Person) -> Unit
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
                onItemClick(person)
            },
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Row(modifier = Modifier.fillMaxWidth(0.50f))
        {
            Image(
                bitmap = ImageBitmap.imageResource(id = com.intive.gradebook.R.drawable.aaa),
                contentDescription = "Random guy",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "${person.firstName} ${person.lastName}",
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(0.60f)) {
            if (addedColumn != "") {
                Text(
                    text = "Lorem ipsum",
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }
        }
        Row()
        {
            Text(
                text = "${4.5}${stringResource(R.string.max_grade)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }
    }
}

