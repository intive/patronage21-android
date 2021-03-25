package com.example.patron_a_tive.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.patron_a_tive.R
import com.example.patron_a_tive.components.PatronativeAppBar
import com.example.patron_a_tive.ui.components.HomeScreenBoxButtonsGrid

class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                NewsStory()
            }
        }
    }
}

@Composable
fun NewsStory() {
    MaterialTheme {
        Scaffold (
            topBar = {
                PatronativeAppBar(
                    title = { Text(text = "Patron-a-tive!", color = Color(R.color.design_default_color_primary)) },
                    actions = {
                        IconButton(onClick = { }){
                            Icon(Icons.Outlined.Search, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        IconButton(onClick = { }){
                            Icon(Icons.Outlined.Person, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        IconButton(onClick = { }){
                            Icon(Icons.Outlined.Dehaze, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
            ){
                Text(
                    text = "Witaj w Patron-a-tive!",
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                )
                Text(
                    text = "Aplikacja Parton-a-tive pozwala na zarządzanie" +
                            "programem stażowym, jego użytkownikami oraz wydarzeniami",
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    HomeScreenBoxButtonsGrid(Modifier.size(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory()
}