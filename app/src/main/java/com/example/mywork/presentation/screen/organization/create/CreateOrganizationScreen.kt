package com.example.mywork.presentation.screen.organization.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.presentation.navigation.Screen
import com.example.mywork.presentation.screen.workers.editing.EditWorkerCommand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrganizationScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateOrganizationViewModel = hiltViewModel(),
    onFinished: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val scrollState = rememberScrollState()

    when (currentState) {
        is CreateOrganizationScreenState.Creation -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.create_organization_title)) },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        viewModel.processCommand(CreateOrganizationCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)

                            )
                        }
                    )

                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.name,

                        singleLine = true,
                        onValueChange = {
                            viewModel.processCommand(CreateOrganizationCommand.InputName(it))
                        },
                        label = { Text(stringResource(R.string.name_organization)) },

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )

                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.inn,
                        singleLine = true,
                        onValueChange = {
                            if (it.length < 13) {
                                viewModel.processCommand(CreateOrganizationCommand.InputInn(it))
                            }
                        },
                        label = { Text(stringResource(R.string.inn)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        ),
//                        supportingText = {
//                            Text(
//                                text = "${(currentState.inn).length} / 12",
//                                modifier = Modifier.fillMaxWidth(),
//                                textAlign = TextAlign.End
//                            )
//                        }

                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.phone,
                        singleLine = true,
                        onValueChange = {
                            viewModel.processCommand(CreateOrganizationCommand.InputPhone(it))
                        },
                        label = { Text(stringResource(R.string.phone)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.email,
                        singleLine = true,
                        onValueChange = {
                            viewModel.processCommand(CreateOrganizationCommand.InputEmail(it))
                        },
                        label = { Text(stringResource(R.string.email)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.address,
                        maxLines = 3,
                        onValueChange = {
                            viewModel.processCommand(CreateOrganizationCommand.InputAddress(it))
                        },
                        label = { Text(stringResource(R.string.address)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.comments,
                        minLines = 3,
                        maxLines = 5,
                        onValueChange = {
                            viewModel.processCommand(CreateOrganizationCommand.InputComments(it))
                        },
                        label = { Text(stringResource(R.string.comments)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { viewModel.processCommand(CreateOrganizationCommand.Save) }
                    ) {
                        Text(text = stringResource(R.string.save))
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