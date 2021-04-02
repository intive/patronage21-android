package com.example.patron_a_tive.calendar_module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.patron_a_tive.R
import com.example.patron_a_tive.calendar_module.components.CancelButton
import com.example.patron_a_tive.calendar_module.components.HeaderLarge
import com.example.patron_a_tive.calendar_module.components.HeaderMedium
import com.example.patron_a_tive.calendar_module.components.OKButton
import java.util.*

class DayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                DayFragmentLayout(findNavController())
            }
        }
    }

    @Composable
    fun DayFragmentLayout(navController: NavController? = null) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {

                HeaderLarge("Piątek, 05.03.2021")
                HeaderMedium("Retrospective", Modifier.padding(bottom = 4.dp))

                Text(
                    "Godzina: 15:00-16:00",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.pale_blue))
                        .padding(12.dp)
                ) {
                    Text(
                        stringResource(R.string.event_users_label),
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary
                    )

                    Text(
                        "10",
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondary
                    )
                }

                UsersList()

            }

            Column {
                // TODO: Add onClick handlers
                OKButton(stringResource(R.string.accept_event), {})
                CancelButton(stringResource(R.string.reject_event), {})
            }

        }
    }

    @Composable
    fun UsersList() {
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(10) {
                UsersListItem(it)
            }
        }
    }

    @Composable
    fun UsersListItem(index: Int) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = R.drawable.header),
                contentDescription = "User's profile pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clip(CircleShape)
            )

            Text(
                "Uczestnik ${index + 1}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 16.dp)
            )
            //Text("Organizator", style = MaterialTheme.typography.subtitle1)
        }
        Divider(color = Color.LightGray)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}