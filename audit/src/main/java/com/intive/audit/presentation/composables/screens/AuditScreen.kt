package com.intive.audit.presentation.composables.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intive.audit.R
import com.intive.audit.audit_screen.Audit
import com.intive.audit.presentation.audit.AuditViewModel
import com.intive.audit.presentation.composables.AuditHeader
import com.intive.ui.PatronageTypography
import com.intive.ui.components.SectionHeader
import com.intive.ui.components.SectionHeaderText
import com.intive.ui.components.TitleText
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AuditScreen(
    modifier: Modifier = Modifier,
    audits: List<Audit> = List(1000) { Audit(1, Date(), "Logowanie", "Adam Kowalski") },
    auditViewModel: AuditViewModel
) {
    Column(modifier) {
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
                text = stringResource(R.string.audit_screen),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
        }
        AuditsList(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            audits = audits,
            auditViewModel = auditViewModel
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AuditsList(audits: List<Audit>, modifier: Modifier = Modifier, auditViewModel: AuditViewModel) {

    val query = auditViewModel.query.value

    val showSearchField = auditViewModel.showSearchField.value

    val showFilterField = auditViewModel.showFilterField.value

    Column(modifier = modifier) {

        AuditHeader(
            query = query,
            onQueryChanged = auditViewModel::onQueryChanged,
            showSearchField = showSearchField,
            showFilterField = showFilterField,
            onSearchIconClick = auditViewModel::onSearchIconClick,
            onFilterIconClick = auditViewModel::onFilterIconClick
        )

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }

        Box(contentAlignment = Alignment.BottomEnd) {
            Column(Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f)
                ) {
                    items(items = audits) { audit ->
                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            AuditField(
                                modifier = Modifier.padding(15.dp),
                                audit = audit
                            )
                        }
                        Divider(
                            color = Color.LightGray,
                            thickness = 0.5.dp
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = showButton
                ) {
                    FloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = 0)
                            }
                        },
                        elevation = FloatingActionButtonDefaults.elevation(
                            pressedElevation = 8.dp, defaultElevation = 2.dp
                        )
                    ) {
                        Icon(
                            Icons.Outlined.ArrowUpward,
                            contentDescription = stringResource(R.string.arrow_upward_icon_desc)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownDemo(expanded: Boolean, updateExpand: (Boolean) -> Unit) {
    val items = listOf("Od najnowszych", "Od najstarszych")
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
            .padding(top = 50.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { updateExpand(false) },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        updateExpand(false)
                    },
                )
                {
                    Text(
                        text = s,
                        modifier = Modifier.weight(1f)
                    )
                    if (index == selectedIndex) {
                        Icon(
                            Icons.Outlined.Done,
                            contentDescription = stringResource(R.string.arrow_upward_icon_desc)
                        )
                    }
                }
                if (index != items.size - 1)
                    Divider(color = Color.LightGray)
            }
        }
    }
}

@Composable
fun AuditField(modifier: Modifier = Modifier, audit: Audit) {
    //TODO: place audit object formatter here
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = audit.date.toString(),
                style = PatronageTypography.subtitle2
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = audit.eventTitle,
                style = PatronageTypography.subtitle2
            )
        }
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = audit.userName,
            style = PatronageTypography.subtitle2
        )
    }
}

//@ExperimentalComposeUiApi
//@ExperimentalAnimationApi
//@Preview
//@Composable
//fun AuditScreenPreview() {
//    AuditScreen()
//}