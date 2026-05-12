package com.nammashaale.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nammashaale.inventory.ui.screens.*
import com.nammashaale.inventory.ui.theme.NammaShaaleTheme
import com.nammashaale.inventory.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NammaShaaleTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                NavHost(navController, startDestination = "dashboard") {
                    composable("dashboard") {
                        DashboardScreen(viewModel) { route ->
                            navController.navigate(route)
                        }
                    }
                    composable("assets") {
                        AssetRegisterScreen(viewModel) { navController.popBackStack() }
                    }
                    composable("healthcheck") {
                        HealthCheckScreen(viewModel) { navController.popBackStack() }
                    }
                    composable("issues") {
                        IssueLogScreen(viewModel) { navController.popBackStack() }
                    }
                    composable("repairs") {
                        RepairRequestScreen(viewModel) { navController.popBackStack() }
                    }
                    composable("report") {
                        SummaryReportScreen(viewModel) { navController.popBackStack() }
                    }
                }
            }
        }
    }
}