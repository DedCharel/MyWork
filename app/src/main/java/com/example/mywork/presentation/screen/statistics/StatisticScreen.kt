package com.example.mywork.presentation.screen.statistics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.domain.statistic.OrganizationStatisticEntity
import com.example.mywork.presentation.utils.DataFormater
import com.example.mywork.presentation.utils.DateRangePickerModal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticViewModel = hiltViewModel(),
    onFinished: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    var showDialog by remember { mutableStateOf(false) }

    when(currentState){
        StatisticScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {

            }
        }
        is StatisticScreenState.OrganizationStatistics -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {Text(stringResource(R.string.statistic_title))},
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
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
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Column(Modifier.fillMaxWidth()) {
                      //  Text(text = stringResource(R.string.date), fontSize = 12.sp)
                      //  Spacer(Modifier.height(4.dp))
                        Text(
                            modifier = Modifier
                                .clickable { showDialog = true }
                                .padding(16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            text = DataFormater.formatDateToString(currentState.currentRange.first) + " - " + DataFormater.formatDateToString(currentState.currentRange.second)
                        )

                        if (showDialog) {
                            DateRangePickerModal (

                                onDateRangeSelected = { millis ->
                                    val startRange = millis.first ?: 0
                                    val finishRange = millis.second ?: 0
                                    viewModel.processCommand(
                                        StatisticCommand.StatisticRange(
                                            Pair(startRange, finishRange)
                                        )
                                    )
                                    showDialog = false
                                },
                                onDismiss = { showDialog = false },
                                startRange = currentState.currentRange

                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(0.6f),
                            text = stringResource(R.string.name_organization)
                        )
                        Text(
                            modifier = Modifier.weight(0.2f),
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.count)
                        )
                        Text(
                            modifier = Modifier.weight(0.2f),
                            textAlign = TextAlign.End,

                            text = stringResource(R.string.hour)
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.padding(8.dp)


                    ) {
                        items(
                            items = currentState.organizationStatistics,
                            key = { it.name }

                        ){
                            StatisticCard(
                                statistic = it,
                                onStatisticClick = {}
                            )
                        }
                    }
                }


            }
        }
    }

}
@Composable
fun StatisticCard(
    modifier: Modifier = Modifier,
    statistic: OrganizationStatisticEntity,
    onStatisticClick: (OrganizationStatisticEntity) -> Unit
){
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onStatisticClick(statistic)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )   {

            Row(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(0.6f),
                    text = "${statistic.name}"
                )
                Text(
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.Center,
                    text = "${statistic.count}"
                )
                Text(
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.End,

                    text = "${statistic.totalTime}"
                )
            }


    }
}