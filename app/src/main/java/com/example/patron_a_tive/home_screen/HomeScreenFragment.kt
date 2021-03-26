package com.example.patron_a_tive.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.patron_a_tive.ui.components.HomeScreenBoxButtonsGrid
import androidx.compose.ui.text.TextStyle


class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NewsStory()
            }
        }
    }
}

@Composable
fun NewsStory(modifier: Modifier = Modifier) {
    MaterialTheme {
        Column(modifier) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = "Witaj w Patron-a-tive!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.Cyan,
                        letterSpacing = 0.sp
                    ),
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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