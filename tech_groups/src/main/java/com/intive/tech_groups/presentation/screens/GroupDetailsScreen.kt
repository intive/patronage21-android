package com.intive.tech_groups.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intive.tech_groups.R
import com.intive.ui.PatronativeTheme
import com.intive.ui.components.SectionHeader
import com.intive.ui.components.TitleText

@Preview
@Composable
fun GroupDetailsScreen() {
    PatronativeTheme() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = dimensionResource(id = R.dimen.screen_padding),
                    end = dimensionResource(id = R.dimen.screen_padding)
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Outlined.ArrowBackIos,
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .weight(1f)
                )
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .weight(1f)
                )
            }
            TitleText(
                text = "Grupa I",
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
            Text(
                text = "Technologie:",
                style = MaterialTheme.typography.h6
            )
            SectionHeader(
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                title = {
                    Text(text = "Opis")
                }
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            )
        }
    }
}