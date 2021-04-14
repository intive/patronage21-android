package com.intive.gradebook.composables.screens

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.gradebook.DetailsViewModel
import com.intive.gradebook.Person
import com.intive.gradebook.R
import com.intive.gradebook.composables.Header
import com.intive.gradebook.composables.GradeListItem

@Composable
fun DetailsScreen(
     navController: NavController,
     user: Person,
     grades: List<DetailsViewModel.Grade>
) {
    val context = LocalContext.current.resources
    val displayMetrics = context.displayMetrics
    val dsize = displayMetrics.heightPixels/displayMetrics.density

    Column (Modifier.height(dsize.dp-115.dp)) {
        Row(Modifier.height(90.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
                contentDescription = null,
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
        Header(
            text = stringResource(R.string.grades),
            count = grades.size, showCount = true
        )
        Column(Modifier.verticalScroll(rememberScrollState())) {
            grades.forEach { grade ->
                GradeListItem(grade)
                Divider(
                    color = Color(0xFFF1F1F1),
                    thickness = 2.dp
                )
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ){
        Header(
            text = stringResource(R.string.average_grade),
            text2 = "${4.5}${stringResource(R.string.max_grade)}",
            showText2 = true,
            backgroundColor = Color(0xFFC2185A),
            textColor = Color.White,
            fraction = 0.85f
        )
    }
}