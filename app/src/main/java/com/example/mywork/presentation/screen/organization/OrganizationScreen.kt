package com.example.mywork.presentation.screen.organization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mywork.domain.organization.Organization


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizationScreen(
    modifier: Modifier = Modifier,
    viewModel: OrganizationViewModel = hiltViewModel(),
    onOrganizationSelected: (Organization) -> Unit
){
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    Scaffold(
        topBar = {
            TopAppBar( title = {
                Text("Организации")
            },

                )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            items(
                items = currentState.organizations,
                key = {it.id}
            ){

                Text(
                    modifier = Modifier.clickable{
                        onOrganizationSelected(it)
                    },
                    text = it.name)
            }
        }

    }
}