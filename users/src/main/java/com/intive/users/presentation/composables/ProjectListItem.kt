package com.intive.users.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.intive.repository.domain.model.Project

@Composable
fun ProjectListItem(project: Project) {
    Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp)) {
        Text(
            text = project.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = project.role
        )
    }
}