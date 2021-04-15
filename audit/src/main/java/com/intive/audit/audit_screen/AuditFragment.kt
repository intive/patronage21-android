package com.intive.audit.audit_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.audit.R
import com.intive.ui.*
import com.intive.ui.components.*
import kotlinx.coroutines.launch
import java.util.*

class AuditFragment : Fragment() {

    private val viewModel: AuditScreenViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    AuditScreen()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun AuditScreen(
    modifier: Modifier = Modifier,
    audits: List<Audit> = List(1000) { Audit(1, Date(), "Logowanie", "Adam Kowalski") }
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
            audits = audits
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun MyScreenContent(
    modifier: Modifier = Modifier,
    audits: List<Audit> = List(1000) { Audit(1, Date(), "Logowanie", "Adam Kowalski") }
) {
    Column(modifier = modifier.fillMaxHeight()) {
        AuditsList(
            audits,
            Modifier
                .weight(1f)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun AuditsList(audits: List<Audit>, modifier: Modifier = Modifier) {

    var showSearchField by remember { mutableStateOf(false) }
    var showFilterField by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = modifier) {
        SectionHeader(
            title = {
                SectionHeaderText(text = "Lista")
            },
            action = {
                AnimatedVisibility(
                    visible = showSearchField
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        label = { Text("Wyszukaj") }
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    showSearchField = !showSearchField
                    if(showSearchField) text = TextFieldValue("")
                }) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = stringResource(R.string.search_icon_desc)
                    )
                }
                IconButton(onClick = { showFilterField = !showFilterField }) {
                    Icon(
                        Icons.Outlined.FilterAlt,
                        contentDescription = stringResource(R.string.filter_icon_desc)
                    )
                    DropdownDemo(
                        expanded = showFilterField,
                        updateExpand = { newExpanded ->
                            showFilterField = !showFilterField
                        }
                    )
                }
            }
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

@ExperimentalAnimationApi
@Preview
@Composable
fun AuditScreenPreview() {
    AuditScreen()
}

@ExperimentalAnimationApi
@Preview
@Composable
fun MyScreenContentPreview() {
    MyScreenContent()
}