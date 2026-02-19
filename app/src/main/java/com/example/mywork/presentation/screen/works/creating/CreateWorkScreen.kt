package com.example.mywork.presentation.screen.works.creating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.presentation.utils.DataFormater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWorkScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateWorkViewModel = hiltViewModel(),
    onFinished: () -> Unit,
    onWorkerClick: () -> Unit,
    onOrganizationClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    when (currentState) {
        is CreateWorkScreenState.Creation -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Create Work",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        viewModel.processCommand(CreateWorkCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Back"

                            )
                        }
                    )
                }
            ) { innerPadding ->

                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {

                    Column {
                        Button(onClick = {
                            showDialog = true
                        }) { Text(DataFormater.formatDateToString(currentState.date)) }

                        if (showDialog) {
                            DatePickerModal(
                                onDateSelected = { millis ->
                                    val currentMills = millis ?: 0
                                    viewModel.processCommand(
                                        CreateWorkCommand.InputDate(
                                            currentMills
                                        )
                                    )
                                    showDialog = false
                                },
                                onDismiss = { showDialog = false }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(onClick = { onOrganizationClick() })
                    ) {
                        Text(
                            text = currentState.organization?.name ?: "Select organization"
                        )
                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(onClick = { onWorkerClick() })
                    ) {
                        Text(
                            text = currentState.worker?.name ?: "Select worker"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        value = currentState.description,
                        onValueChange = {
                            viewModel.processCommand(CreateWorkCommand.InputDescription(it))
                        },
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        placeholder = {
                            Text(
                                text = "Description",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = currentState.time.toString(),
                        onValueChange = {
                            viewModel.processCommand(CreateWorkCommand.InputTime(it))
                        },
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        placeholder = {
                            Text(
                                text = "Time",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        onClick = { viewModel.processCommand(CreateWorkCommand.Save) }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }

        CreateWorkScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}