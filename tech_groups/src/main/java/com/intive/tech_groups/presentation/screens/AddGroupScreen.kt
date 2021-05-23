package com.intive.tech_groups.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.repository.util.Resource
import com.intive.tech_groups.Constants.SPACER_HEIGHT
import com.intive.tech_groups.presentation.viewmodels.AddGroupViewModel
import com.intive.ui.components.TitleText
import com.intive.tech_groups.R
import com.intive.ui.components.CheckBoxesList
import com.intive.ui.components.ErrorItem

@Composable
fun AddGroupScreen(
    viewModel: AddGroupViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val name by viewModel.name.observeAsState()
    val technologies = viewModel.technologies.value

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
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        when (technologies) {
            is Resource.Loading -> {
                Text(stringResource(R.string.downloading_technologies))
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                CheckBoxesList(
                    title = stringResource(R.string.choose_technologies_for_group_text),
                    onErrorText = "",
                    items = technologies.data!!,
                    onItemSelected = viewModel::updateTechnologies
                )
            }
            is Resource.Error -> {
                ErrorItem(message = stringResource(R.string.error)) {

                }
            }
        }
    }
}