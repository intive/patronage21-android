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
import com.intive.gradebook.DetailsViewModel
import com.intive.gradebook.R

@Composable
fun GradeListItem(grade: DetailsViewModel.Grade) {
    var g=grade.grade
    var i=g.toInt()
    var fin=""
    if(g==i.toDouble())
        fin=i.toString() + stringResource(R.string.max_grade)
    else
        fin=g.toString() + stringResource(R.string.max_grade)
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier=Modifier.fillMaxWidth()) {
            Text(
                text = grade.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(0.9f)
            )
            Text(
                text=fin,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = stringResource(R.string.ground),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = grade.grounds,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}