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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.patron_a_tive.ui.components.HomeScreenBoxButtonsGrid
import androidx.compose.ui.text.TextStyle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.patron_a_tive.R
import com.example.patron_a_tive.ui.theme.PatronageTypography
import com.example.patron_a_tive.ui.theme.PatronativeTheme


class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NewsStory(navController = findNavController())
            }
        }
    }
    private fun findNavController(): NavController {
        val navHostFragment =
            (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}

@Composable
fun NewsStory(modifier: Modifier = Modifier, navController: NavController? = null) {
    PatronativeTheme {
        Column(modifier) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = stringResource(R.string.home_screen_greeting),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                )
                Text(
                    text = stringResource(R.string.home_screen_text),
                    style = PatronageTypography.body2,
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeScreenBoxButtonsGrid(
                        modifier = Modifier.size(20.dp),
                        navController = navController
                    )
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