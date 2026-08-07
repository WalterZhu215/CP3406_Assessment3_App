package com.example.natureexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.natureexplorer.ui.theme.NatureExplorerTheme
import com.example.natureexplorer.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NatureExplorerTheme {
                NatureExplorerApp()
            }
        }
    }
}

@Composable
fun NatureExplorerApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 控制底部导航栏在详情页时隐藏
    val bottomBarDestination = listOf("home", "explore", "collection", "profile").any { it == currentRoute }

    Scaffold(
        bottomBar = {
            if (bottomBarDestination) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Place, contentDescription = "Explore") },
                        label = { Text("Explore") },
                        selected = currentRoute == "explore",
                        onClick = {
                            navController.navigate("explore") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Collection") },
                        label = { Text("Collection") },
                        selected = currentRoute == "collection",
                        onClick = {
                            navController.navigate("collection") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = currentRoute == "profile",
                        onClick = {
                            navController.navigate("profile") {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }

            // ExploreScreen 传入 onTrailClick 回调，触发带参数的导航
            composable("explore") {
                ExploreScreen(
                    onTrailClick = { trailName ->
                        navController.navigate("detail/$trailName")
                    }
                )
            }

            composable("collection") { CollectionScreen() }
            composable("profile") { ProfileScreen() }

            // 注册新的详情页路由，支持接收 {trailName} 参数
            composable(
                route = "detail/{trailName}",
                arguments = listOf(navArgument("trailName") { type = NavType.StringType })
            ) { backStackEntry ->
                // 解析传过来的参数
                val trailName = backStackEntry.arguments?.getString("trailName") ?: "Unknown Trail"

                DetailScreen(
                    trailName = trailName,
                    onBackClick = { navController.popBackStack() } // 返回上一页
                )
            }
        }
    }
}
