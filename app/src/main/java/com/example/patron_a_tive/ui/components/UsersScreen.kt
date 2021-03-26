package com.example.patron_a_tive.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patron_a_tive.ui.utils.darkBlue
import com.example.patron_a_tive.users_module.users_screen.Person

@Composable
fun UsersScreen(people: List<Person>) {

    LazyColumn {
        item {
            Text(
                "Użytkownicy",
                fontSize = 20.sp,
                color = darkBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 8.dp,
                    top = 16.dp
                )
            )
            Text(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac dolor et dui dictum viverra vel eu erat.",
                modifier = Modifier.padding(
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 16.dp,
                    top = 8.dp
                )
            )
            Search(query = "", onQueryChanged = { }, onExecuteSearch = { })
        }
        item {
            Header(text = "Liderzy", count = people.size, showCount = true)
        }

        itemsIndexed(people) { index, person ->
            PersonListItem(person = person)
            Divider(color = Color(0xFFF1F1F1), thickness = 2.dp)
        }

        item {
            Header(text = "Uczestnicy", count = people.size, showCount = true)
        }

        itemsIndexed(people) { index, person ->
            PersonListItem(person = person)
            Divider(color = Color(0xFFF1F1F1), thickness = 2.dp)
        }
    }

}