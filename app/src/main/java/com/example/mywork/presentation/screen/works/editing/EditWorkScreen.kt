package com.example.mywork.presentation.screen.works.editing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.presentation.screen.works.editing.EditWorkCommand.InputDate
import com.example.mywork.presentation.screen.works.editing.EditWorkCommand.InputDescription
import com.example.mywork.presentation.screen.works.editing.EditWorkCommand.InputTime
import com.example.mywork.presentation.utils.DataFormater
import com.example.mywork.presentation.utils.DatePickerModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWorkScreen(
    modifier: Modifier = Modifier,
    workId:Int,
    viewModel: EditWorkViewModel = hiltViewModel(
        creationCallback = {factory: EditWorkViewModel.Factory ->
            factory.create(workId)
        }
    ),
    currentWorkerId: Long? = null,
    currentOrganizationId: Long? = null,
    onFinished: () -> Unit,
    onWorkerClick: () -> Unit,
    onOrganizationClick: () -> Unit
){
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val scrollState = rememberScrollState()

    currentWorkerId?.let {
        viewModel.processCommand(EditWorkCommand.InputWorker(it))
    }
    currentOrganizationId?.let {
        viewModel.processCommand(EditWorkCommand.InputOrganization(it))
    }

    when (currentState) {
        is EditWorkState.Editing -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.edit_work_title),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        actions = {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        viewModel.processCommand(
                                            EditWorkCommand.DeleteWork(
                                                currentState.work
                                            )
                                        )
                                    }
                                    .padding(horizontal = 16.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete)
                            )
                        },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        viewModel.processCommand(EditWorkCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)

                            )
                        }
                    )
                }
            ) { innerPadding ->

                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .verticalScroll(scrollState)

                ) {

                    Column(Modifier.fillMaxWidth()) {
                        Text(text = stringResource(R.string.date), fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.clickable { showDialog = true },
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            text = DataFormater.formatDateToString(currentState.work.date)
                        )

                        if (showDialog) {
                            DatePickerModal(

                                onDateSelected = { millis ->
                                    val currentMills = millis ?: 0
                                    viewModel.processCommand(
                                        InputDate(
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
                        Text(text = stringResource(R.string.organization), fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            text = currentState.work.organization.name
                        )
                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier

                            .fillMaxWidth()
                            .clickable(onClick = { onWorkerClick() })
                    ) {
                        Text(text = stringResource(R.string.worker), fontSize = 12.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            text = currentState.work.worker.name ,

                            )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        minLines = 3,
                        value = currentState.work.description,
                        onValueChange = {
                            viewModel.processCommand(
                                InputDescription(it)
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        label = { Text(stringResource(R.string.description)) },

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = currentState.work.time.toString(),
                            onValueChange = { input ->
                                if (input.isNotEmpty() && input.matches(Regex("^\\d*\\.?\\d*\$"))) {

                                        viewModel.processCommand(
                                            InputTime(
                                                input
                                            )
                                        )
                                }
                            },
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            label = { Text(stringResource(R.string.hour)) },
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
                        onClick = { viewModel.processCommand(EditWorkCommand.Save) }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
        EditWorkState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
        EditWorkState.Initial -> {}
    }

}