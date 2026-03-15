package com.example.mywork.presentation.navigation

import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mywork.presentation.screen.organization.OrganizationScreen
import com.example.mywork.presentation.screen.organization.create.CreateOrganizationScreen
import com.example.mywork.presentation.screen.organization.editing.EditOrganizationScreen
import com.example.mywork.presentation.screen.settings.SettingsScreen
import com.example.mywork.presentation.screen.statistics.OrganizationStatisticScreen
import com.example.mywork.presentation.screen.statistics.StatisticScreen
import com.example.mywork.presentation.screen.workers.WorkerScreen
import com.example.mywork.presentation.screen.workers.create.CreateWorkerScreen
import com.example.mywork.presentation.screen.workers.editing.EditWorkerScreen
import com.example.mywork.presentation.screen.works.WorksScreen
import com.example.mywork.presentation.screen.works.creating.CreateWorkScreen
import com.example.mywork.presentation.screen.works.editing.EditWorkScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Works.route
    ) {
        composable(Screen.Works.route) {
            WorksScreen(
                onWorkClick = {
                    navController.navigate(Screen.EditWork.createRoute(it))
                },
                onSettingsClick = {
                    navController.navigate("settings")
                },
                onAddWorkClick = {
                    navController.navigate(Screen.CreateWork.route)
                },
                onStatisticClick = {
                    navController.navigate(Screen.Statistic.route)
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
                    navController.navigate("worker/true")
                },
                onOrganizationClick = {
                    navController.navigate("organization/true")
                }
            )
        }
        composable(Screen.EditWork.route) { entry ->
            val currentWorkerId = entry.savedStateHandle.get<Long>("worker_id")
            val currentOrganizationId = entry.savedStateHandle.get<Long>("organization_id")
            val workId = Screen.EditWork.getWorkId(entry.arguments)
            EditWorkScreen(
                modifier = Modifier.padding(16.dp),
                currentWorkerId = currentWorkerId,
                currentOrganizationId = currentOrganizationId,
                workId = workId,
                onFinished = { navController.popBackStack() },
                onWorkerClick = {
                    navController.navigate("worker/true")
                },
                onOrganizationClick = {
                    navController.navigate("organization/true")
                }


            )
        }
        composable(Screen.Worker.route) {
            val isChoice = Screen.Worker.getStatus(it.arguments)
            WorkerScreen(
                onAddClick = {
                    navController.navigate("create_worker")
                },
                isChoice = isChoice,
                onWorkerSelected = { worker ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("worker_id", worker.id)
                    navController.popBackStack()
                },
                onEditWorker = {
                    navController.navigate(Screen.EditWorker.createRoute(it.id) )
                },
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Organization.route) {
            val isChoice = Screen.Organization.getStatus(it.arguments)
            OrganizationScreen(
                isChoice = isChoice,
                onAddClick = {
                    navController.navigate("create_organization")
                },
                onEditOrganization = {
                    navController.navigate(Screen.EditOrganization.createRoute(it.id))
                },
                onOrganizationSelected = { organization ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("organization_id", organization.id)
                   navController.popBackStack()
                },
                onFinished = {
                    navController.popBackStack()
                })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onWorkerClick = { navController.navigate("worker/false") },
                onOrganizationClick = { navController.navigate("organization/false") },
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.CreateOrganization.route) {
            CreateOrganizationScreen(
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.CreateWorker.route) {
            CreateWorkerScreen(
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.EditWorker.route) {
            val workerId = Screen.EditWorker.getWorkerId(it.arguments)
            EditWorkerScreen(
                workerId = workerId,
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.EditOrganization.route) {
            val organizationId = Screen.EditOrganization.getOrganizationId(it.arguments)
            EditOrganizationScreen(
                organizationId = organizationId,
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Statistic.route) {
            StatisticScreen(
                onStatisticClick = {
                    navController.navigate(Screen.OrganizationStatistic.createRoute(it))
                },
                onFinished = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.OrganizationStatistic.route) {
            val organizationId = Screen.OrganizationStatistic.getOrganizationId(it.arguments)
            OrganizationStatisticScreen (
                organizationId = organizationId,
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

    data object EditWork : Screen("edit_work/{workId}") {
        fun createRoute(workId: Int): String {
            return "edit_work/$workId"
        }

        fun getWorkId(arguments: Bundle?): Int {
            return arguments?.getString("workId")?.toInt() ?: 0
        }
    }

    data object Worker : Screen("worker/{choice}") {

        fun getStatus(arguments: Bundle?): Boolean {
            return arguments?.getString("choice")?.toBoolean() ?: false
        }
    }

    data object Organization : Screen("organization/{choice}") {

        fun getStatus(arguments: Bundle?): Boolean {
            return arguments?.getString("choice")?.toBoolean() ?: false
        }
    }

    data object Settings : Screen("settings")

    data object CreateOrganization : Screen("create_organization")

    data object CreateWorker : Screen("create_worker")

    data object EditWorker : Screen("edit_worker/{workerId}") {
        fun createRoute(workerId: Long): String {
            return "edit_worker/$workerId"
        }

        fun getWorkerId(arguments: Bundle?): Long {
            return arguments?.getString("workerId")?.toLong() ?: 0
        }
    }

    data object EditOrganization : Screen("edit_organization/{organizationId}") {
        fun createRoute(organizationId: Long): String {
            return "edit_organization/$organizationId"
        }

        fun getOrganizationId(arguments: Bundle?): Long {
            return arguments?.getString("organizationId")?.toLong() ?: 0
        }
    }

    data object Statistic : Screen("statistic")

    data object OrganizationStatistic : Screen("organization_statistic/{organizationId}") {
        fun createRoute(organizationId: Long): String {
            return "organization_statistic/$organizationId"
        }

        fun getOrganizationId(arguments: Bundle?): Long {
            return arguments?.getString("organizationId")?.toLong() ?: 0
        }
    }
}


