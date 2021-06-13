package com.intive.gradebook.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.intive.gradebook.R

@Composable
fun GradesListItem(gradeName: String, grade: Float, gradeReview: String) {
    val gradeInt = grade.toInt()
    val gradePrint: String
    if (grade == gradeInt.toFloat())
        gradePrint = gradeInt.toString() + stringResource(R.string.max_grade)
    else
        gradePrint = grade.toString() + stringResource(R.string.max_grade)
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = gradeName,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            )
            Text(
                text = gradePrint,
                fontWeight = FontWeight.Bold
            )
        }
        /*Text(
            text = stringResource(R.string.ground),
            fontWeight = FontWeight.Bold
        )*/
        Text(
            text = gradeReview,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}