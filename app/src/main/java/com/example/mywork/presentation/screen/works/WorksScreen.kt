package com.example.mywork.presentation.screen.works

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.domain.work.Work
import com.example.mywork.presentation.utils.DataFormater

@Composable
fun WorksScreen(
    modifier: Modifier = Modifier,
    viewModel: WorksViewModel = hiltViewModel(),
    onAddWorkClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    val currentState = state.value

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddWorkClick ,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add work"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            items(
                items = currentState.works,
                key = {it.id}
            ){work->
                WorkCard(
                    modifier.padding(8.dp),
                    work =work,
                    onWorkClick = {viewModel.processCommand(WorkCommand.EditWork(work))})
            }
        }
    }
}

@Composable
fun WorkCard(
    modifier: Modifier = Modifier,
    work: Work,
    onWorkClick: (Work) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = {
                onWorkClick(work)
            }
            )
            .padding(8.dp)

    ) {
        Row {
            Text(
                text = DataFormater.formatDateToString(work.date),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = work.organization,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = work.description,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 3
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = work.worker,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = work.time.toString(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }

    }
}