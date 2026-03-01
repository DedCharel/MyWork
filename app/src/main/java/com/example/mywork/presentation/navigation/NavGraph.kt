package com.example.mywork.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mywork.presentation.screen.organization.OrganizationScreen
import com.example.mywork.presentation.screen.organization.create.CreateOrganizationScreen
import com.example.mywork.presentation.screen.settings.SettingsScreen
import com.example.mywork.presentation.screen.workers.WorkerScreen
import com.example.mywork.presentation.screen.workers.create.CreateWorkerScreen
import com.example.mywork.presentation.screen.works.creating.CreateWorkScreen
import com.example.mywork.presentation.screen.works.WorksScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Works.route
    ) {
        composable(Screen.Works.route) {
            WorksScreen(
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onAddWorkClick = {
                    navController.navigate(Screen.CreateWork.route)
                }
            )
        }
        composable(Screen.CreateWork.route) { entry ->
            val currentWorkerId = entry.savedStateHandle.get<Long>("worker_id")
            val currentOrganizationId = entry.savedStateHandle.get<Long>("organization_id")
            CreateWorkScreen(
                modifier = Modifier.padding(16.dp),
                currentWorkerId = currentWorkerId,
                currentOrganizationId = currentOrganizationId,
                onFinished = { navController.popBackStack() },
                onWorkerClick = {
                    navController.navigate("worker")
                },
                onOrganizationClick = {
                    navController.navigate("organization")
                }
            )
        }
        composable(Screen.Worker.route) {
            WorkerScreen(
                onAddClick = {
                    navController.navigate("create_worker")
                },
                onWorkerSelected = {worker ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("worker_id", worker.id)
                    navController.popBackStack()
            })
        }
        composable(Screen.Organization.route) {
            OrganizationScreen(
                onAddClick = {
                    navController.navigate("create_organization")
                },
                onOrganizationSelected = {organization ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("organization_id", organization.id)
                    navController.popBackStack()
                })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onWorkerClick = {},
                onOrganizationClick = {}
            )
        }
        composable(Screen.CreateOrganization.route) {
            CreateOrganizationScreen(
                onFinished = {
                  navController.popBackStack()
                }
            )
        }
        composable(Screen.CreateWorker.route){
            CreateWorkerScreen(
                onFinished = {
                    navController.popBackStack()
                }
            )
        }

    }

}

sealed class Screen(val route: String) {

    data object Works : Screen("works")

    data object CreateWork : Screen("create_work")

    data object Worker: Screen("worker")

    data object Organization: Screen("organization")

    data object Settings: Screen("settings")

    data object CreateOrganization: Screen("create_organization")

    data object CreateWorker: Screen("create_worker")


}