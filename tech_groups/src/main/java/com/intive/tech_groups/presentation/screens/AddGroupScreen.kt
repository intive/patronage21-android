package com.intive.tech_groups.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.repository.util.Resource
import com.intive.tech_groups.Constants.SPACER_HEIGHT
import com.intive.tech_groups.Constants.SUCCESS_KEY
import com.intive.tech_groups.presentation.viewmodels.AddGroupViewModel
import com.intive.ui.components.TitleText
import com.intive.tech_groups.R
import com.intive.tech_groups.presentation.fragments.AddGroupFragmentDirections
import com.intive.ui.components.CheckBoxesList
import com.intive.ui.components.ErrorItem
import com.intive.ui.components.PrimaryButton
import kotlinx.coroutines.launch

@Composable
fun AddGroupScreen(
    viewModel: AddGroupViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val name: String by viewModel.name.observeAsState("")
    val technologies = viewModel.technologies.value
    val response = viewModel.responseState.value

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        when (response) {
            is Resource.Success -> {
                val action = AddGroupFragmentDirections.actionBackToGroups(SUCCESS_KEY)
                navController.navigate(action)
            }
            is Resource.Error -> {
                Text(
                    text = response.message?: stringResource(R.string.error_occured),
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(SPACER_HEIGHT))
            }
        }
        TitleText(
            text = stringResource(R.string.add_group),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            value = name,
            onValueChange = viewModel::onNameChange,
            label = { Text(text = stringResource(R.string.group_name)) }
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
        PrimaryButton(text = stringResource(R.string.create_group),
            onClick = { viewModel.addGroup() }
        )
    }
}