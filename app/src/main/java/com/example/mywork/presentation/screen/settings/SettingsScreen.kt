package com.example.mywork.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onOrganizationClick: () -> Unit,
    onWorkerClick: () -> Unit,
    onDefaultClick: () -> Unit,
    onFinished: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val scrollState = rememberScrollState()

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
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        viewModel.processCommand(SettingsCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)

                            )
                        }
                    )
                },
                bottomBar = {

                }
            ) {innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(top = 8.dp)
                        .verticalScroll(scrollState)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clickable { onOrganizationClick() }
                    ){
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = stringResource(R.string.organizations)
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = stringResource(R.string.organizations)
                        )
                    }

                    HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clickable { onWorkerClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = stringResource(R.string.workers)
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = stringResource(R.string.workers)
                        )
                    }
                    HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clickable {
                                onDefaultClick()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = stringResource(R.string.default_settings)
                        )
                    }
                    HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clickable {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = stringResource(R.string.unload)
                        )
                        Text(

                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = stringResource(R.string.upload_data)
                        )
                    }
                    HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.info)
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = stringResource(R.string.about_the_program)
                        )
                    }
                    HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                }
            }
        }
    }

}