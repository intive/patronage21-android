package com.intive.users.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.intive.users.DetailsViewModel

@Composable
fun ProjectListItem(project: DetailsViewModel.Project) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = project.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = project.role
        )
    }
}