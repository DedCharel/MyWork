package com.example.mywork.presentation.screen.organization.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrganizationScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateOrganizationViewModel = hiltViewModel(),
    onFinished: () -> Unit,
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    when(currentState){
        is CreateOrganizationScreenState.Creation -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {Text("Create organization")}
                    )

                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentState.name,
                        onValueChange = {
                            viewModel.processCommand(CreateOrganizationCommand.InputName(it))
                        },
                        placeholder = {
                            Text("Organization name")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        onClick = { viewModel.processCommand(CreateOrganizationCommand.Save) }
                    ) {
                        Text(text = "Save")
                    }
                }

            }
        }
        CreateOrganizationScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
    }
}