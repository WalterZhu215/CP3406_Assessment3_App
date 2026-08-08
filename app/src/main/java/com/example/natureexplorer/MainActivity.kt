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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.natureexplorer.ui.theme.NatureExplorerTheme
import com.example.natureexplorer.ui.screens.*
import com.example.natureexplorer.ui.viewmodels.CollectionViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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


    val sharedCollectionViewModel: CollectionViewModel = viewModel()

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

            composable("explore") {
                ExploreScreen(
                    onTrailClick = { trailName, imageUrl ->
                        val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                        navController.navigate("detail/$trailName?imageUrl=$encodedUrl")
                    }
                )
            }


            composable("collection") { CollectionScreen(viewModel = sharedCollectionViewModel) }

            composable("profile") { ProfileScreen() }

            composable(
                route = "detail/{trailName}?imageUrl={imageUrl}",
                arguments = listOf(
                    navArgument("trailName") { type = NavType.StringType },
                    navArgument("imageUrl") { type = NavType.StringType; defaultValue = "" }
                )
            ) { backStackEntry ->
                val trailName = backStackEntry.arguments?.getString("trailName") ?: "Unknown Trail"
                val encodedUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                val imageUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())

                DetailScreen(
                    trailName = trailName,
                    imageUrl = imageUrl.ifEmpty { "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?q=80&w=2560&auto=format&fit=crop" },
                    onBackClick = { navController.popBackStack() },
                    collectionViewModel = sharedCollectionViewModel //
                )
            }
        }
    }
}
