package com.example.mywork.presentation.screen.works

import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.domain.work.Work
import com.example.mywork.presentation.utils.DataFormater

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorksScreen(
    modifier: Modifier = Modifier,
    viewModel: WorksViewModel = hiltViewModel(),
    onWorkClick: (Int) -> Unit,
    onSettingsClick: () -> Unit,
    onAddWorkClick: () -> Unit,
    onStatisticClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    val currentState = state.value

    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "Work list") },
                    actions = {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onStatisticClick() }
                                .padding(end = 16.dp),
                            painter = painterResource(id = R.drawable.ic_equalizer),
                            contentDescription = "Statistics"
                        )
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onSettingsClick() }
                                .padding(end = 16.dp),
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                )
                SearchBar(
                    modifier = Modifier
                        .padding(8.dp),
                    query = state.value.query,
                    onQueryChange = {
                        viewModel.processCommand(WorkCommand.InputSearchQuery(it))
                    }
                )
            }

        },
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
                Modifier.padding(innerPadding)
            ) {
                items(
                    items = currentState.works,
                    key = { it.id }
                ) { work ->
                    WorkCard(

                        work = work,
                        onWorkClick = { onWorkClick(it.id) })
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
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onWorkClick(work)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ){
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
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
                    text = work.organization.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = work.description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = work.worker.name,
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

}
@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit
){
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(10.dp)
            ),
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Search",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search work",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        shape = RoundedCornerShape(10.dp)
    )
}