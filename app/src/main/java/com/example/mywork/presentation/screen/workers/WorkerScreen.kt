package com.example.mywork.presentation.screen.workers

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.domain.worker.Worker
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkerViewModel = hiltViewModel(),
    onWorkerSelected: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    Scaffold(
        topBar = {
            TopAppBar( title = {
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
                key = {it.id}
            ){
             Text(text = it.name)
            }
        }

    }
}