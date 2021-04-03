package com.intive.users.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intive.users.DetailsViewModel
import com.intive.users.Person
import com.intive.users.R
import com.intive.users.composables.Header
import com.intive.users.composables.ProjectListItem

@Composable
fun DetailsScreen(
     navController: NavController,
     user: Person,
     projects: List<DetailsViewModel.Project>
) {
    Column(
        Modifier
            .height(90.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.aaa),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "${user.firstName} ${user.lastName}",
                modifier = Modifier
                    .padding(start = 8.dp),
                fontSize = 22.sp,
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Column {
        Header(text = "Bio")
        Text(
            text = stringResource(id = R.string.lorem_placeholder),
            modifier = Modifier
                .padding(16.dp)
        )
    }

    Column {
        Header(text = "Projekty", count = projects.size, showCount = true)
        projects.forEach { project ->
            ProjectListItem(project)
            Divider(
                color = Color(0xFFF1F1F1),
                thickness = 2.dp
            )
        }
    }

    Column {
        Header(text = "Kontakt")
        Text("a@b.c", modifier = Modifier.padding(16.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(
                "Wyślij wiadomość",
                color = Color.White
            )
        }
        Divider(
            color = Color(0xFFF1F1F1),
            thickness = 2.dp,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        )
        Text("+48 123123123", modifier = Modifier.padding(16.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(
                "Zadzwoń",
                color = Color.White
            )
        }
        Divider(
            color = Color(0xFFF1F1F1),
            thickness = 2.dp,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
        )
        Text("GitHub/loremIpsum", modifier = Modifier.padding(16.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
        ) {
            Text(
                "Otwórz link",
                color = Color.White
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Edytuj profil",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        Button(
            onClick = {
                navController.navigate(R.id.action_detailsFragment_to_deactivateUserDialogFragment)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Dezaktywuj profil",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}