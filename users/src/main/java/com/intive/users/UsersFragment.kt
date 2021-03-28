package com.intive.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.users.ui.utils.darkBlue
import com.intive.users.ui.utils.lightBlue
import com.intive.users.composables.Header
import com.intive.users.composables.PersonListItem
import com.intive.users.composables.Search

data class Person(val firstName: String, val lastName: String)

class UsersFragment : Fragment() {

    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val navController = findNavController()

        return ComposeView(requireContext()).apply {
            setContent {
                Column {
                    MaterialTheme {
                        val users = viewModel.users
                        val query = ""

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
                                Search(query = query, onQueryChanged = {  }, onExecuteSearch = {})
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
                                PersonListItem(person = person, onItemClick = {})
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
                                PersonListItem(person = person, onItemClick = {  })
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
                }
            }
        }
    }
}