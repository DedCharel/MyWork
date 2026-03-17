package com.example.mywork.presentation.screen.statistics

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.R
import com.example.mywork.domain.work.Work
import com.example.mywork.presentation.utils.DataFormater
import com.example.mywork.presentation.utils.DateRangePickerModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizationStatisticScreen(
    modifier: Modifier = Modifier,
    organizationId: Long,
    startRange: Long,
    finishRange: Long,
    viewModel: OrganizationStatisticViewModel = hiltViewModel(
        creationCallback = { factory: OrganizationStatisticViewModel.Factory ->
            factory.create(organizationId, startRange, finishRange)
        }
    ),
    onFinished: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current


    when (currentState) {
        is OrganizationStatisticScreenState.DisplayOrganizationStatistic -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.organization_statistic)) },
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
            )
            { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            onClick = { showDialog = true }
                        ) {
                            Text(
                                text = DataFormater.formatDateToString(currentState.currentRange.first) + " - " + DataFormater.formatDateToString(
                                    currentState.currentRange.second
                                )
                            )
                        }
                        if (showDialog) {
                            DateRangePickerModal(

                                onDateRangeSelected = { millis ->
                                    val startRange = millis.first ?: 0
                                    val finishRange = millis.second ?: 0
                                    viewModel.processCommand(
                                        OrganizationStatisticCommand.SetStatisticRange(
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

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        text = currentState.organizationName,
                        textAlign = TextAlign.Center
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()


                    ) {
                        items(
                            items = currentState.works,
                            key = { it.id }

                        ) { work ->
                            OrganizationStatisticCard(work = work)
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        //temp solution
                                        val title = context.getString(
                                            R.string.report_work_for_the_period,
                                            DataFormater.formatDateToString(currentState.currentRange.first),
                                            DataFormater.formatDateToString(currentState.currentRange.second)
                                        )
                                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:")
                                            val report =
                                                putExtra(
                                                    Intent.EXTRA_EMAIL,
                                                    arrayOf(currentState.organizationEmail)
                                                )
                                            putExtra(Intent.EXTRA_SUBJECT, title)
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                currentState.works.joinToString(separator = "\n") {
                                                    DataFormater.formatDateToString(it.date) + " " + it.description + " / " + it.time + " " + context.getString(R.string.hour_short) + "/"

                                                } + "\n\n" + context.getString(R.string.report_footer))
                                        }
                                        context.startActivity(
                                            Intent.createChooser(
                                                intent,
                                                context.getString(
                                                    R.string.select_an_email_client
                                                )
                                            )
                                        )


                                    }
                                ) {
                                    Text(stringResource(R.string.send_by_email))
                                }
                            }

                        }
                    }
                }
            }
        }

        OrganizationStatisticScreenState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
    }
}

@Composable
fun OrganizationStatisticCard(
    modifier: Modifier = Modifier,
    work: Work
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                text = DataFormater.formatDateToString(work.date)
            )
            Text(
                fontWeight = FontWeight.Bold,
                text = "${work.time}"
            )
        }
        Text(
            modifier = modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Start,
            text = work.description
        )
    }


}