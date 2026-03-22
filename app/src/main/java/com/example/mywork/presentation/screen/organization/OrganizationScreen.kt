package com.example.mywork.presentation.screen.organization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.dropUnlessResumed
import com.example.mywork.R
import com.example.mywork.domain.organization.Organization


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizationScreen(
    modifier: Modifier = Modifier,
    viewModel: OrganizationViewModel = hiltViewModel(),
    isChoice: Boolean,
    onAddClick: () -> Unit,
    onEditOrganization: (Organization) -> Unit,
    onOrganizationSelected: (Organization) -> Unit,
    onFinished: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    when(currentState){
        OrganizationState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
        is OrganizationState.OrganizationScreenState -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(R.string.organizations_title))
                        },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clickable {
                                        viewModel.processCommand(OrganizationCommand.Back)
                                    },
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = stringResource(R.string.back)

                            )
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
                        }

                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding
                ) {
                    items(
                        items = currentState.organizations,
                        key = { it.id }
                    ) {

                        OrganizationCard(
                            organization = it,
                            onOrganizationClick = dropUnlessResumed {
                                if (isChoice){
                                    onOrganizationSelected(it)
                                } else {
                                    onEditOrganization(it)
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
fun OrganizationCard(
    modifier: Modifier = Modifier,
    organization: Organization,
    onOrganizationClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onOrganizationClick()
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
                fontSize = 14.sp,
                text = organization.name,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),

                ) {
                Text(text = "phone: " + organization.phone, fontSize = 12.sp)
                if (!organization.email.isEmpty())
                    Text("email: " + organization.email, fontSize = 12.sp)
            }

        }
    }
}