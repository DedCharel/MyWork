package com.example.mywork.presentation.screen.works.creating

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.presentation.screen.workers.WorkerCommand
import com.example.mywork.presentation.utils.DataFormater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWorkScreen(
    modifier: Modifier = Modifier,
    currentWorkerId: Long? = null,
    currentOrganizationId: Long? = null,
    viewModel: CreateWorkViewModel = hiltViewModel(),
    onFinished: () -> Unit,
    onWorkerClick: () -> Unit,
    onOrganizationClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    currentWorkerId?.let {
        viewModel.processCommand(CreateWorkCommand.InputWorker(it))
    }
    currentOrganizationId?.let {
        viewModel.processCommand(CreateWorkCommand.InputOrganization(it))
    }

    when (currentState) {
        is CreateWorkScreenState.Creation -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Create Work",
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
                    modifier = Modifier
                        .padding(innerPadding)

                ) {

                    Column(Modifier.fillMaxWidth()) {
                        Text(text = "Date:", fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.clickable { showDialog = true },
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            text = DataFormater.formatDateToString(currentState.date)
                        )

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

                    Column(
                        modifier = Modifier

                            .fillMaxWidth()
                            .clickable(onClick = { onOrganizationClick() })

                    ) {
                        Text(text = "Organization:", fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            text = currentState.organization?.name ?: "Select organization"
                        )
                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier

                            .fillMaxWidth()
                            .clickable(onClick = { onWorkerClick() })
                    ) {
                        Text(text = "Worker:", fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            text = currentState.worker?.name ?: "Select worker",

                            )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        minLines = 3,
                        value = currentState.description,
                        onValueChange = {
                            viewModel.processCommand(
                                CreateWorkCommand.InputDescription(it))
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        label = { Text("Description") },

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = currentState.timeHour.toString(),
                            onValueChange = { input ->
                                if (input.isNotEmpty() && input.isDigitsOnly()) {
                                    if (input.toInt() > 24)
                                        viewModel.processCommand(
                                            CreateWorkCommand.InputTimeHour("24"))
                                    else
                                        viewModel.processCommand(
                                            CreateWorkCommand.InputTimeHour(
                                                input
                                            )
                                        )
                                } else {
                                    viewModel.processCommand(
                                        CreateWorkCommand.InputTimeHour("0"))
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            label = { Text("Hour") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Spacer(Modifier.padding(16.dp))
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = currentState.timeMinute.toString(),
                            onValueChange = { input ->
                                if (input.isNotEmpty() && input.isDigitsOnly()) {
                                    if (input.toInt() > 59)
                                        viewModel.processCommand(
                                            CreateWorkCommand.InputTimeMinute("59"))
                                    else
                                        viewModel.processCommand(
                                            CreateWorkCommand.InputTimeMinute(
                                                input
                                            )
                                        )
                                } else {
                                    viewModel.processCommand(
                                        CreateWorkCommand.InputTimeMinute("0"))
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            label = { Text("Minute") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
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