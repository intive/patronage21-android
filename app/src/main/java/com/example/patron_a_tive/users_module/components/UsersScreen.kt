package com.example.patron_a_tive.users_module.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patron_a_tive.ui.utils.darkBlue
import com.example.patron_a_tive.ui.utils.lightBlue
import com.example.patron_a_tive.users_module.users_screen.Person

@Composable
fun UsersScreen(
    users: List<Person>,
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    onItemClick: (Person) -> Unit
) {

    val modifier = Modifier.padding(
        start = 30.dp,
        end = 30.dp,
        bottom = 8.dp,
        top = 16.dp
    )

    LazyColumn {
        item {
            Text(
                "Użytkownicy",
                fontSize = 20.sp,
                color = darkBlue,
                fontWeight = FontWeight.Bold,
                modifier = modifier
            )
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac dolor et dui dictum viverra vel eu erat.",
                modifier = modifier
            )
            Search(query = query, onQueryChanged = onQueryChanged, onExecuteSearch = onExecuteSearch)
        }


        item {
            Header(
                text = "Liderzy",
                count = users.size,
                showCount = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .background(lightBlue)
            )
        }

        items(users) {person ->
            PersonListItem(person = person, onItemClick = onItemClick)
            Divider(
                color = Color(0xFFF1F1F1),
                thickness = 2.dp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }

        item {
            Header(
                text = "Uczestnicy",
                count = users.size,
                showCount = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .background(lightBlue)
            )
        }

        items(users) {person ->
            PersonListItem(person = person, onItemClick = onItemClick)
            Divider(
                color = Color(0xFFF1F1F1),
                thickness = 2.dp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }
    }

}