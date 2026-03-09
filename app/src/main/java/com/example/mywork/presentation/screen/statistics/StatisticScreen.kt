package com.example.mywork.presentation.screen.statistics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticViewModel = hiltViewModel(),
    onFinished: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    when(currentState){
        StatisticScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {

            }
        }
        is StatisticScreenState.OrganizationStatistics -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.statistic_title)) },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        onFinished()
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
                    items = currentState.organizationStatistics,
                    key = { it.name }

                ){
                    Text("${it.name} time: ${it.totalTime} count: ${it.count}")
                }
                }

            }
        }
    }

}