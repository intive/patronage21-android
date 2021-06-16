package com.intive.patronative.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import androidx.navigation.Navigation.findNavController
import com.intive.patronative.ui.components.PatronativeAppBar
import com.intive.patronative.R
import com.intive.shared.NavigationViewModel
import com.intive.ui.PatronativeTheme
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class NavActivity : AppCompatActivity() {

    private val navigationViewModel by viewModel<NavigationViewModel>()

    var navController: NavController? = null;
    @IdRes var startDestination: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(
            application, "b5843062-fd8e-4699-9533-a396f7f8c4b9",
            Analytics::class.java,
            Crashes::class.java
        )

        setContentView(R.layout.content_main).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {

                val loginState = navigationViewModel.loggedState.value
                val userLogin = navigationViewModel.userLogin.value

                PatronativeTheme {
                    PatronativeAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.app_name),
                                color = MaterialTheme.colors.primary
                            )
                        },
                        actions = {
                            if(loginState == NavigationViewModel.LoginEvent.UserLoggedIn) {
                                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.appbar_icons_spacer_size)))

                                IconButton(onClick = {
                                    navController?.navigate(Uri.parse("intive://userDetails/$userLogin"))
                                }) {
                                    Icon(
                                        Icons.Outlined.Person,
                                        contentDescription = stringResource(R.string.profile_icon_desc)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        navController = findNavController(R.id.nav_host_fragment)

        lifecycleScope.launchWhenStarted {
            navigationViewModel.loginFlow.collect { event ->

                val navGraph = navController!!.navInflater.inflate(R.navigation.nav_graph)
                navController!!.graph = navGraph

                // Allows to pass deep-links without being overshadowed by the action below
                if(intent.action == Intent.ACTION_VIEW)
                    return@collect

                when(event) {
                    NavigationViewModel.LoginEvent.UserLoggedIn -> {
                        startDestination = R.id.nav_graph
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph, false)
                            .build()
                        navController!!.navigate(startDestination, null, navOptions)
                    }
                    NavigationViewModel.LoginEvent.UserLoggedOut -> {
                        startDestination = R.id.registration_nav_graph
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(R.id.nav_graph, true)
                            .build()
                        navController!!.navigate(startDestination, null, navOptions)
                    }
                }
            }
        }
    }

}