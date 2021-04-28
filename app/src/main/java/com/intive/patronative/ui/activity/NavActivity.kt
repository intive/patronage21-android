package com.intive.patronative.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.intive.patronative.ui.components.PatronativeAppBar
import com.intive.patronative.R
import com.intive.patronative.databinding.ContentMainBinding
import com.intive.ui.PatronativeTheme
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;


class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(
            application, "b5843062-fd8e-4699-9533-a396f7f8c4b9",
            Analytics::class.java,
            Crashes::class.java
        )

        setContentView(R.layout.content_main).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {
                PatronativeTheme {
                    PatronativeAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.app_name),
                                color = MaterialTheme.colors.primary
                            )
                        },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Outlined.Search,
                                    contentDescription = stringResource(R.string.search_icon_desc)
                                )
                            }
                            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.appbar_icons_spacer_size)))
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Outlined.Person,
                                    contentDescription = stringResource(R.string.profile_icon_desc)
                                )
                            }
                            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.appbar_icons_spacer_size)))
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Outlined.Dehaze,
                                    contentDescription = stringResource(R.string.settings_icon_desc)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}