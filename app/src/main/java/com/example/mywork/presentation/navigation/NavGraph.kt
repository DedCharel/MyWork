package com.example.mywork.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mywork.presentation.screen.creating.CreateWorkScreen
import com.example.mywork.presentation.screen.editing.EditWorkScreen
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
        composable(Screen.CreateWork.route) {
            CreateWorkScreen(
                modifier = Modifier.padding(16.dp),
                onFinished = { navController.popBackStack() },
                onWorkerClick = {},
                onOrganizationClick = {
                  //  navController.navigate(Screen.EditWork.route)
                }
            )

        }
//        composable(Screen.EditWork.route) {
//            EditWorkScreen(
//
//            )
//
//        }
    }

}

sealed class Screen(val route: String) {

    data object Works : Screen("works")

    data object CreateWork : Screen("create_work")

  //  data object EditWork: Screen("edit_work")


}