package com.example.mywork.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mywork.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScreen(
    modifier: Modifier = Modifier,
    viewModel: DefaultViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val scrollState = rememberScrollState()

    when(currentState){
        DefaultScreenState.Display -> {
            Scaffold(
                topBar = {TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.default_settings))
                            },
                    navigationIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clickable {
                                    viewModel.processCommand(DefaultScreenCommand.Back)
                                },
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back)

                        )
                    },
                )
                }
            ) { innerPaddings ->
                Column(
                    modifier = Modifier.padding(innerPaddings)
                ) {  }

            }
        }
        DefaultScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
    }
}