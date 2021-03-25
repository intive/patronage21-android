package com.example.patron_a_tive.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patron_a_tive.R

@Composable
fun BoxButton(text: String,
              onClick: (() -> Unit)){
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
            Image(modifier = Modifier
                .height(50.dp)
                .width(50.dp),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Module miniature",
                contentScale = ContentScale.Crop)
            Text(text = text, textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis,)
        }
    }
}

@Preview
@Composable
fun HomeScreenUsersButton(){
    BoxButton(text = "Użytkownicy", onClick = { })
}

@Preview
@Composable
fun HomeScreenTechGroupsButton(){
    BoxButton(text = "Grupy technologiczne", onClick = { })
}

@Composable
fun HomeScreenBoxButtonsGrid(modifier: Modifier = Modifier){
    Column {
        Row {
            BoxButton(text = "Grupy technologiczne", onClick = { })
            Spacer(modifier = modifier)
            BoxButton(text = "Użytkownicy", onClick = { })
        }
        Spacer(modifier = modifier)
        Row {
            BoxButton(text = "Dzienniczek", onClick = { })
            Spacer(modifier = modifier)
            BoxButton(text = "Kalendarz", onClick = { })
        }
        Spacer(modifier = modifier)
        Row {
            BoxButton(text = "Audyt zdarzeń", onClick = { })
        }
    }
}

@Preview
@Composable
fun HomeScreenBoxButtonsGridPreview(){
    HomeScreenBoxButtonsGrid(Modifier.size(20.dp))
}