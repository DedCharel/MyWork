package com.example.mywork.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mywork.presentation.screen.workers.WorkerScreen
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
                onAddWorkClick = {
                    navController.navigate(Screen.CreateWork.route)
                }
            )
        }
        composable(Screen.CreateWork.route) { entry ->
            val currentWorkerId = entry.savedStateHandle.get<Long>("key_id")
            CreateWorkScreen(
                modifier = Modifier.padding(16.dp),
                currentWorkerId = currentWorkerId,
                onFinished = { navController.popBackStack() },
                onWorkerClick = {
                    navController.navigate("worker")
                },
                onOrganizationClick = {
                }
            )
        }
        composable(Screen.Worker.route) {
            WorkerScreen(
                onWorkerSelected = {worker ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("key_id", worker.id)
                    navController.popBackStack()
            })
        }

    }

}

sealed class Screen(val route: String) {

    data object Works : Screen("works")

    data object CreateWork : Screen("create_work")

    data object Worker: Screen("worker")


}