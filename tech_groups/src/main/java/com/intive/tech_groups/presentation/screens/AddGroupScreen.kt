package com.intive.tech_groups.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.tech_groups.presentation.viewmodels.AddGroupViewModel
import com.intive.ui.components.TitleText
import com.intive.tech_groups.R

@Composable
fun AddGroupScreen(
    viewModel: AddGroupViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val name by viewModel.name.observeAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TitleText(
            text = stringResource(R.string.add_group),
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}