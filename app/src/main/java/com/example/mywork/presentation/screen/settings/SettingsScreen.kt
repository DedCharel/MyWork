package com.example.mywork.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.presentation.screen.workers.create.CreateWorkerViewModel.CreateWorkerCommand


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onOrganizationClick: () -> Unit,
    onWorkerClick: () -> Unit,
    onFinished: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    when (currentState){
        SettingsState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
        SettingsState.ShowSettings -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.settings_title)) },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        viewModel.processCommand(SettingsCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)

                            )
                        }
                    )
                }
            ) {innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onOrganizationClick() },
                        fontWeight = FontWeight.Bold,
                        text = stringResource(R.string.organizations)
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { onWorkerClick() },
                        fontWeight = FontWeight.Bold,
                        text = stringResource(R.string.workers)
                    )

                }
            }
        }
    }

}