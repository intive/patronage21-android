package com.intive.patronative.home_screen

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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.patronative.ui.components.HomeScreenBoxButtonsGrid
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.intive.patronative.R
import com.intive.registration.screens.SuccessScreen
import com.intive.registration.viewmodels.RegistrationSuccessDialogState
import com.intive.registration.viewmodels.SharedViewModel
import com.intive.ui.PatronageTypography
import com.intive.ui.PatronativeTheme
import com.intive.ui.components.*


class HomeScreenFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    HomeScreen(navController = findNavController())
                    if(sharedViewModel.successDialogState==RegistrationSuccessDialogState.SHOW_DIALOG) {
                        SuccessScreen()
                        sharedViewModel.successDialogState=RegistrationSuccessDialogState.HIDE_DIALOG
                    }
                }
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
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController? = null) {
    Column(modifier) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.screen_padding),
                    end = dimensionResource(id = R.dimen.screen_padding),
                )
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            TitleText(
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


@Preview
@Composable
fun DefaultPreview() {
    HomeScreen()
}