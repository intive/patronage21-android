package com.example.patron_a_tive.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import com.example.patron_a_tive.FragmentAwareAndroidViewBinding
import com.example.patron_a_tive.R
import com.example.patron_a_tive.ui.components.PatronativeAppBar
import com.example.patron_a_tive.databinding.ContentMainBinding
import com.example.patron_a_tive.ui.theme.PatronativeTheme
import kotlinx.android.synthetic.main.fragment_add_event.*


class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    PatronativeTheme {
        Scaffold(
            topBar = {
                PatronativeAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = MaterialTheme.colors.primary
                        )
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Outlined.Search, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        IconButton(onClick = { }) {
                            Icon(Icons.Outlined.Person, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        IconButton(onClick = { }) {
                            Icon(Icons.Outlined.Dehaze, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            FragmentAwareAndroidViewBinding(ContentMainBinding::inflate)
        }
    }

}