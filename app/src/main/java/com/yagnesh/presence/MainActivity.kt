package com.yagnesh.presence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.yagnesh.presence.ui.screens.*
import com.yagnesh.presence.ui.theme.PresenceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PresenceTheme {
                PresenceApp()
            }
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {

    object Home : BottomNavItem("home", "Home", Icons.Default.Home)

    object Reports : BottomNavItem("reports", "Reports", Icons.Default.BarChart)

    object Students : BottomNavItem("students", "Students", Icons.Default.Person)

    object Profile : BottomNavItem("profile", "Profile", Icons.Default.AccountCircle)

}

@Composable
fun PresenceApp() {

    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Reports,
        BottomNavItem.Students,
        BottomNavItem.Profile
    )

    Scaffold(

        bottomBar = {

            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->

                    NavigationBarItem(

                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },

                        label = { Text(item.title) },

                        selected = currentRoute == item.route,

                        onClick = {

                            navController.navigate(item.route) {

                                popUpTo(navController.graph.startDestinationId)

                                launchSingleTop = true

                            }

                        }

                    )

                }

            }

        }

    ) { innerPadding ->

        NavHost(

            navController = navController,

            startDestination = BottomNavItem.Home.route,

            modifier = Modifier.padding(innerPadding)

        ) {

            composable(BottomNavItem.Home.route) {

                HomeScreen(navController)

            }

            composable(

                route = "camera/{className}",

                arguments = listOf(

                    navArgument("className") {
                        type = NavType.StringType
                    }

                )

            ) { backStackEntry ->

                CameraAttendanceScreen(
                    navBackStackEntry = backStackEntry
                )

            }

            composable(BottomNavItem.Reports.route) {

                ReportsScreen()

            }

            composable(BottomNavItem.Students.route) {

                StudentsScreen()

            }

            composable(BottomNavItem.Profile.route) {

                ProfileScreen()

            }

        }

    }

}