package com.example.mywork.presentation.screen.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onOrganizationClick: () -> Unit,
    onWorkerClick: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable{onOrganizationClick()},
                fontWeight = FontWeight.Bold,
                text = "Organizations"
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable{onWorkerClick()},
                fontWeight = FontWeight.Bold,
                text = "Workers"
            )

        }
    }
}