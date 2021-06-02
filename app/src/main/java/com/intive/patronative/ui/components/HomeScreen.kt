package com.intive.patronative.ui.components

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intive.patronative.R
import com.intive.ui.PatronageTypography
import com.intive.ui.components.BoxButton

@Preview
@Composable
fun HomeScreenUsersButton(){
    BoxButton(text = stringResource(R.string.users_module), onClick = { }){
        Icon(
            Icons.Outlined.Person,
            contentDescription = stringResource(R.string.users_module_miniature_desc),
            Modifier.fillMaxSize(0.3f)
        )
    }
}

@Preview
@Composable
fun HomeScreenTechGroupsButton(){
    BoxButton(text = stringResource(R.string.tech_groups_module), onClick = { }){
        Icon(
            Icons.Outlined.Keyboard,
            contentDescription = stringResource(R.string.tech_groups_module_miniature_desc),
            Modifier.fillMaxSize(0.3f)
        )
    }
}

@Composable
fun HomeScreenBoxButtonsGrid(modifier: Modifier = Modifier, navController: NavController? = null){ // null for Previews
    Column {
        Row {
            Column (Modifier.weight(1f)) {
                BoxButton(
                    text = stringResource(R.string.tech_groups_module),
                    onClick = { navController?.navigate(Uri.parse("https://patronative.page.link/techGroups/MainFragment")) },
                ) {
                    Icon(
                        Icons.Outlined.Keyboard,
                        contentDescription = stringResource(R.string.tech_groups_module_miniature_desc),
                        Modifier.fillMaxSize(0.3f)
                    )
                }
            }
            Spacer(modifier = modifier)
            Column (Modifier.weight(1f)) {
                BoxButton(
                    text = stringResource(R.string.users_module),
                    onClick = { navController?.navigate(Uri.parse("intive://usersFragment")) }
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = stringResource(R.string.users_module_miniature_desc),
                        Modifier.fillMaxSize(0.3f)
                    )
                }
            }
        }
        Spacer(modifier = modifier)
        Row {
            Column (Modifier.weight(1f)){
                BoxButton(
                    text = stringResource(R.string.diary_module),
                    onClick = { navController?.navigate(Uri.parse("https://patronative.page.link/gradebookFragment")) }
                ){
                    Icon(
                        Icons.Outlined.Book,
                        contentDescription = stringResource(R.string.diary_module_miniature_desc),
                        Modifier.fillMaxSize(0.3f)
                    )
                }
            }
            Spacer(modifier = modifier)
            Column (Modifier.weight(1f)) {
                BoxButton(
                    text = stringResource(R.string.calendar_module),
                    onClick = { navController?.navigate(Uri.parse("https://patronative.page.link/calendarFragment")) }
                ) {
                    Icon(
                        Icons.Outlined.CalendarToday,
                        contentDescription = stringResource(R.string.calendar_module_miniature_desc),
                        Modifier.fillMaxSize(0.3f)
                    )
                }
            }
        }
        Spacer(modifier = modifier)
        Row {
            Column (Modifier.weight(1f)) {
                BoxButton(
                    text = stringResource(R.string.logs_module),
                    onClick = { navController?.navigate(Uri.parse("https://patronative.page.link/auditFragment")) }
                ) {
                    Icon(
                        Icons.Outlined.MenuBook,
                        contentDescription = stringResource(R.string.logs_module_miniature_desc),
                        Modifier.fillMaxSize(0.3f)
                    )
                }
            }
            Spacer(modifier = modifier)
            Column (Modifier.weight(1f)) {
                BoxButton(
                    text = stringResource(R.string.registration_module),
                    onClick = { navController?.navigate(Uri.parse("https://patronative.page.link/loginFragment")) }
                ) {
                    Icon(
                        Icons.Outlined.PersonAdd,
                        contentDescription = stringResource(R.string.registration_module_miniature_desc),
                        Modifier.fillMaxSize(0.3f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenBoxButtonsGridPreview(){
    HomeScreenBoxButtonsGrid(Modifier.size(20.dp))
}