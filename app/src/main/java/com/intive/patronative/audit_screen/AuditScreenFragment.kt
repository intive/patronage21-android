package com.intive.patronative.audit_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.intive.patronative.R
import com.intive.patronative.home_screen.HomeScreen
import com.intive.patronative.home_screen.HomeScreenViewModel
import com.intive.ui.PatronativeTheme
import com.intive.ui.TitleText

class AuditScreenFragment : Fragment() {

    private val viewModel: AuditScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PatronativeTheme {
                    AuditScreen()
                }
            }
        }
    }
}

@Composable
fun AuditScreen(modifier: Modifier = Modifier){
    Column(modifier) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.activity_padding),
                    end = dimensionResource(id = R.dimen.activity_padding),
                )
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            TitleText(
                text = stringResource(R.string.audit_screen),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
            )
        }
    }
}