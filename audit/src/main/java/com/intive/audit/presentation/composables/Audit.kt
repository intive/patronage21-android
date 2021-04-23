package com.intive.audit.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intive.repository.domain.model.Audit
import com.intive.ui.PatronageTypography
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Audit(audit: Audit) {

    val dateFormat = audit.date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));

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
                text = dateFormat,
                style = PatronageTypography.body1
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                    modifier = Modifier
                            .fillMaxWidth(0.50f)
                            .wrapContentWidth(Alignment.Start),
                    text = audit.userName,
                    style = PatronageTypography.body1
            )
            Text(
                    modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End),
                    text = audit.id,
                    style = PatronageTypography.body1
            )
        }
//        Text(
//             text = audit.userName,
//             style = PatronageTypography.body1
//        )
    }
}