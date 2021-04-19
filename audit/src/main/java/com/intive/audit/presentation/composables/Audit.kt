package com.intive.audit.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intive.audit.domain.Audit
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
            audit.title?.let { title ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .wrapContentWidth(Alignment.Start),
                    text = title,
                    style = PatronageTypography.body1
                )
            }
            audit.date?.let { date ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                    text = date,
                    style = PatronageTypography.body1
                )
            }
        }
        audit.userName?.let { userName ->
            Spacer(modifier = Modifier.size(15.dp))
            Text(
                text = userName,
                style = PatronageTypography.body1
            )
        }
    }
}