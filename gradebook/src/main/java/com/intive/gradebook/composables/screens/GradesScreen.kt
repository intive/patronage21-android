package com.intive.gradebook.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.intive.gradebook.R
import com.intive.gradebook.composables.GradebookHeader
import com.intive.gradebook.composables.GradesListItem
import com.intive.repository.domain.model.GradebookUser

@ExperimentalComposeUiApi
@Composable
fun GradesScreen(
    user: GradebookUser
) {
    val lazyListState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxHeight()) {
        Column {
            Row(
                Modifier.height(90.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
                    contentDescription = "Random guy",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp)
                        .width(50.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    modifier = Modifier
                        .padding(start = 8.dp),
                    fontSize = 22.sp,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        GradebookHeader(
            text_col1 = stringResource(R.string.grades),
            count = user.grades.size, showCount = true
        )
        for (i in 0 until user.grades.size) {
            GradesListItem(user.gradeNames[i], user.grades[i], user.gradeReviews[i])
            Divider()
        }
        Column {
            GradebookHeader(
                text_col1 = stringResource(R.string.average_grade),
                text_col2 = "${user.averageGrade}${stringResource(R.string.max_grade)}",
                showText2 = true,
                backgroundColor = MaterialTheme.colors.primaryVariant,
                textColor = Color.White,
                fraction = 0.85f
            )
        }
    }
}