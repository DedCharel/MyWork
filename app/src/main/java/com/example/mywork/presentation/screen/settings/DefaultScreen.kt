package com.example.mywork.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.presentation.screen.workers.editing.EditWorkerCommand
import com.example.mywork.presentation.screen.works.creating.CreateWorkCommand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScreen(
    modifier: Modifier = Modifier,
    viewModel: DefaultViewModel = hiltViewModel(),
    currentWorkerId: Long? = null,
    onFinished: () -> Unit,
    onWorkerClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val scrollState = rememberScrollState()

    currentWorkerId?.let {
        viewModel.processCommand(DefaultScreenCommand.InputWorker(it))
    }

    when(currentState){
        is DefaultScreenState.Display -> {
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
                    modifier = modifier
                        .padding(innerPaddings)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = stringResource(R.string.worker), fontSize = 12.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        modifier = Modifier.clickable{
                            onWorkerClick()
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        text = currentState.workerName.ifBlank { stringResource(R.string.select_worker)  } ,

                        )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        onClick = { viewModel.processCommand(DefaultScreenCommand.Save) }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }

            }
        }
        DefaultScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
    }
}