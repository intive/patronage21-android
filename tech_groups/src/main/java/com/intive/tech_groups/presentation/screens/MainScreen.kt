package com.intive.tech_groups.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.viewmodels.MainViewModel
import com.intive.ui.PatronageTypography
import com.intive.ui.components.BoxButton
import com.intive.ui.components.Spinner
import com.intive.ui.components.TitleText

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val filteredList by viewModel.filteredList.observeAsState()

    Column() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.screen_padding),
                    end = dimensionResource(id = R.dimen.screen_padding),
                )
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            TitleText(
                text = stringResource(R.string.tech_groups_title),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
            Text(
                text = "tutaj będzie ekran główny :)",
                style = PatronageTypography.body2,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
            Spinner(items = viewModel.filters) {
                viewModel.filterList(it)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var index = 0
                while (!filteredList.isNullOrEmpty() && index < filteredList!!.size) {
                    Row {
                        Column(Modifier.weight(1f)) {
                            BoxButton(
                                text = filteredList!![index].name,
                                onClick = { },
                            ) {
                                Text("Technologie: ")
                                for (technology in filteredList!![index].techList) {
                                    Text(technology)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Column(Modifier.weight(1f)) {
                            if (index + 1 < filteredList!!.size) {
                                BoxButton(
                                    text = filteredList!![index + 1].name,
                                    onClick = { },
                                    contentOnTop = false
                                ) {
                                    Text("Technologie: ")
                                    for (technology in filteredList!![index + 1].techList) {
                                        Text(technology)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    index += 2
                }
            }
        }
    }
}