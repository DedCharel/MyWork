package com.example.mywork.presentation.screen.organization.editing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.presentation.screen.workers.editing.EditWorkerCommand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOrganizationScreen(
    modifier: Modifier = Modifier,
    organizationId: Long,
    viewModel: EditOrganizationViewModel = hiltViewModel(
        creationCallback = { factory: EditOrganizationViewModel.Factory ->
            factory.create(organizationId)
        }
    ),
    onFinished: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val scrollState = rememberScrollState()

    when(currentState){
        is EditOrganizationState.Editing -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.edit_organization_title)) },
                        actions = {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        viewModel.processCommand(
                                            EditOrganizationCommand.DeleteOrganization(
                                                currentState.organization.id
                                            )
                                        )
                                    }
                                    .padding(end = 16.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete)
                            )
                        },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        viewModel.processCommand(EditOrganizationCommand.Back)
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
                        value = currentState.organization.name,

                        singleLine = true,
                        onValueChange = {
                            viewModel.processCommand(EditOrganizationCommand.InputName(it))
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
                        value = currentState.organization.inn,
                        singleLine = true,
                        onValueChange = {
                            if (it.length < 13) {
                                viewModel.processCommand(EditOrganizationCommand.InputInn(it))
                            }
                        },
                        label = { Text(stringResource(R.string.inn)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        ),
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        value = currentState.organization.phone,
                        singleLine = true,
                        onValueChange = {
                            viewModel.processCommand(EditOrganizationCommand.InputPhone(it))
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
                        value = currentState.organization.email,
                        singleLine = true,
                        onValueChange = {
                            viewModel.processCommand(EditOrganizationCommand.InputEmail(it))
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
                        value = currentState.organization.address,
                        maxLines = 3,
                        onValueChange = {
                            viewModel.processCommand(EditOrganizationCommand.InputAddress(it))
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
                        value = currentState.organization.comments,
                        minLines = 3,
                        maxLines = 5,
                        onValueChange = {
                            viewModel.processCommand(EditOrganizationCommand.InputComments(it))
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
                        onClick = { viewModel.processCommand(EditOrganizationCommand.Save) }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }

            }
        }
        EditOrganizationState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
        EditOrganizationState.Initial -> {}
    }
}