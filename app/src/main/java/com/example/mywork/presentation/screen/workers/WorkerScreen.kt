package com.example.mywork.presentation.screen.workers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mywork.domain.organization.Organization
import com.example.mywork.domain.worker.Worker
import com.example.mywork.presentation.navigation.Screen
import com.example.mywork.presentation.screen.works.WorkCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkerViewModel = hiltViewModel(),
    onWorkerSelected: (Worker) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Работники")
                },

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
                    onWorkerClick = {onWorkerSelected(it)}
                )
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