package com.intive.audit.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intive.repository.domain.model.Audit
import com.intive.ui.PatronageTypography

@Composable
fun Audit(audit: Audit) {
    Column(
        modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .wrapContentWidth(Alignment.Start),
                text = audit.title,
                style = PatronageTypography.body1
            )
            Text(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                text = audit.date,
                style = PatronageTypography.body1
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(
             text = audit.userName,
             style = PatronageTypography.body1
        )
    }
}