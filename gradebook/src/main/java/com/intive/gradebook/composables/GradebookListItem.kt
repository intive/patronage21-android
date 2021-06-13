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
import com.intive.repository.domain.model.Gradebook
import com.intive.gradebook.R

@Composable
fun GradebookListItem(

    gradebook: Gradebook,
    addedColumn: String = "",
    onItemClick: (Gradebook) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onItemClick(gradebook)
            },
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Row(modifier = Modifier
            .fillMaxWidth(0.50f)
            .align(Alignment.CenterVertically))
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
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "${gradebook.firstName} ${gradebook.lastName}",
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth(0.60f)
            .align(Alignment.CenterVertically)) {
            var text: String
            if (addedColumn == "")
            else {
                if (addedColumn == "Grupa") {
                    text = gradebook.group
                } else if (addedColumn == "Ostatnia ocena") {
                    text = gradebook.entries[gradebook.entries.size - 1].grade.toString()
                } else {
                    var stage: Int
                    if (addedColumn == "I")
                        stage = 0
                    else if (addedColumn == "II")
                        stage = 1
                    else if (addedColumn == "III")
                        stage = 2
                    else if (addedColumn == "IV")
                        stage = 3
                    else if (addedColumn == "V")
                        stage = 4
                    else
                        stage = 5

                    if (stage >= gradebook.entries.size)
                        text = 0.toString()
                    else
                        text = gradebook.entries[stage].grade.toString()
                }
                Text(
                    text = text,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Row(modifier = Modifier.align(Alignment.CenterVertically))
        {
            Text(
                text = "${gradebook.averageGrade}${stringResource(R.string.max_grade)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
