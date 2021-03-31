package com.intive.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.users.composables.Header
import com.intive.users.composables.ProjectListItem
import com.intive.users.ui.utils.darkBlue
import com.intive.users.ui.utils.pink

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mock = viewModel.user
        val projects = viewModel.projects

        return ComposeView(requireContext()).apply {
            setContent {
                val scrollState = rememberScrollState()
                Column(
                    Modifier.verticalScroll(scrollState)
                ) {
                    Column(
                        Modifier
                            .height(90.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                bitmap = ImageBitmap.imageResource(id = com.intive.users.R.drawable.aaa),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .width(50.dp)
                                    .height(50.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "${mock.firstName} ${mock.lastName}",
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                fontSize = 22.sp,
                                color = darkBlue,
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
                            colors = ButtonDefaults.buttonColors(backgroundColor = pink),
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
                            colors = ButtonDefaults.buttonColors(backgroundColor = pink),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        ) {
                            Text("Zadzwoń",
                                color = Color.White)
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
                            colors = ButtonDefaults.buttonColors(backgroundColor = pink),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        ) {
                            Text("Otwórz link",
                                color = Color.White)
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = pink),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(
                                    start = 32.dp,
                                    end= 32.dp,
                                    bottom = 16.dp
                                )
                                .fillMaxWidth()
                                .height(60.dp)
                        ) {
                            Text("Edytuj profil",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = darkBlue),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(
                                    start = 32.dp,
                                    end= 32.dp,
                                    bottom = 16.dp
                                )
                                .fillMaxWidth()
                                .height(60.dp)
                        ) {
                            Text("Dezaktywuj profil",
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    }

                }

            }
        }
    }
}