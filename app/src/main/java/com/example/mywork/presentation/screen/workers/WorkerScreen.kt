package com.example.mywork.presentation.screen.workers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.domain.worker.Worker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkerViewModel = hiltViewModel(),
    isChoice: Boolean = false,
    onAddClick: () -> Unit,
    onEditWorker: (Worker) -> Unit,
    onFinished:() -> Unit,
    onWorkerSelected: (Worker) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    when(currentState){
        WorkerState.Finished -> {
            onFinished()
        }
        is WorkerState.WorkerScreenState -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(R.string.workers_title))
                        },
                        actions = {
                            Icon(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable { onAddClick() }
                                    .padding(end = 16.dp),
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(R.string.add)
                            )
                        },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        viewModel.processCommand(WorkerCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)

                            )
                        }

                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding
                ) {
                    items(
                        items = currentState.workers,
                        key = { it.id }
                    ) {

                        WorkerCard(
                            worker = it,
                            onWorkerClick = {
                                if (isChoice){
                                    onWorkerSelected(it)
                                } else {
                                    onEditWorker(it)
                                }

                            }
                        )
                    }

                }
            }
        }
    }

}

@Composable
fun WorkerCard(
    modifier: Modifier = Modifier,
    worker: Worker,
    onWorkerClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onWorkerClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = worker.name,
                fontWeight = FontWeight.Bold
            )

        }
    }
}