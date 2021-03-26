package com.example.patron_a_tive.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.patron_a_tive.R

@Composable
fun BoxButton(text: String,
              onClick: (() -> Unit) = {},
            content: @Composable ()->Unit){
    Box(modifier = Modifier
        .requiredSize(150.dp, 150.dp)
        .border(border = BorderStroke(1.dp, Color(R.color.design_default_color_primary)), shape = RoundedCornerShape(20.dp))
        .clip(shape = RoundedCornerShape(20.dp))
        .clickable { onClick }){
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,) {
            content()
            Text(text = text, textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis,)
        }
    }
}

@Preview
@Composable
fun HomeScreenUsersButton(){
    BoxButton(text = "Użytkownicy", onClick = { }){
        Image(modifier = Modifier
            .height(50.dp)
            .width(50.dp),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Module miniature",
            contentScale = ContentScale.Crop)
    }
}

@Preview
@Composable
fun HomeScreenTechGroupsButton(){
    BoxButton(text = "Grupy technologiczne", onClick = { }){
        Image(modifier = Modifier
            .height(50.dp)
            .width(50.dp),
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Module miniature",
            contentScale = ContentScale.Crop)
    }
}

@Composable
fun HomeScreenBoxButtonsGrid(modifier: Modifier = Modifier, navController: NavController? = null){ // null for Previews
    Column {
        Row {
            BoxButton(
                text = stringResource(R.string.tech_groups_module),
                onClick = { /*TODO: Place technology groups module navDestination here*/ },
            ){
                Icon(
                    Icons.Outlined.Keyboard,
                    contentDescription = "technologyGroupsModule miniature",
                    Modifier.requiredSize(40.dp)
                )
            }
            Spacer(modifier = modifier)
            BoxButton(
                text = stringResource(R.string.users_module),
                onClick = { /*TODO: Place users module navDestination here*/ }
            ){
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "usersModule miniature",
                    Modifier.requiredSize(40.dp)
                )
            }
        }
        Spacer(modifier = modifier)
        Row {
            BoxButton(
                text = stringResource(R.string.diary_module),
                onClick = { /*TODO: Place diary module navDestination here*/ }
            ){
                Icon(
                    Icons.Outlined.Book,
                    contentDescription = "diaryModule miniature",
                    Modifier.requiredSize(40.dp)
                )
            }
            Spacer(modifier = modifier)
            BoxButton(
                text = stringResource(R.string.calendar_module),
                onClick = { /*TODO: Place calendar module navDestination here*/ }
            ){
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = "calendarModule miniature",
                    Modifier.requiredSize(40.dp)
                )
            }
        }
        Spacer(modifier = modifier)
        Row {
            BoxButton(
                text = stringResource(R.string.logs_module)
                , onClick = { /*TODO: Place logs module navDestination here*/ }
            ){
                Icon(
                    Icons.Outlined.MenuBook,
                    contentDescription = "logsModule miniature",
                    Modifier.requiredSize(40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenBoxButtonsGridPreview(){
    HomeScreenBoxButtonsGrid(Modifier.size(20.dp))
}