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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.natureexplorer.data.QuizRepository
import com.example.natureexplorer.data.TrailDatabase
import com.example.natureexplorer.data.TrailRepository
import com.example.natureexplorer.ui.screens.CollectionScreen
import com.example.natureexplorer.ui.screens.DetailScreen
import com.example.natureexplorer.ui.screens.ExploreScreen
import com.example.natureexplorer.ui.screens.HomeScreen
import com.example.natureexplorer.ui.screens.ProfileScreen
import com.example.natureexplorer.ui.screens.QuizScreen
import com.example.natureexplorer.ui.theme.NatureExplorerTheme
import com.example.natureexplorer.ui.viewmodels.CollectionViewModel
import com.example.natureexplorer.ui.viewmodels.CollectionViewModelFactory
import com.example.natureexplorer.ui.viewmodels.QuizViewModel
import com.example.natureexplorer.ui.viewmodels.QuizViewModelFactory
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


val LocalIsEnglish =
    compositionLocalOf { true }


class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

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

    var isEnglish by
    remember {
        mutableStateOf(true)
    }


    /*
     * Learning difficulty will later be controlled
     * by the Settings screen.
     *
     * For now, Medium is the default.
     */
    var quizDifficulty by
    remember {
        mutableStateOf("Medium")
    }


    val context =
        LocalContext.current


    /*
     * Room database
     */
    val database =
        remember {

            TrailDatabase
                .getDatabase(context)
        }


    /*
     * Repositories
     */
    val trailRepository =
        remember {

            TrailRepository(
                database.trailDao()
            )
        }


    val quizRepository =
        remember {

            QuizRepository(
                database.quizResultDao()
            )
        }


    /*
     * Shared Collection ViewModel
     */
    val sharedCollectionViewModel:
            CollectionViewModel =

        viewModel(

            factory =
                CollectionViewModelFactory(
                    trailRepository
                )
        )


    val navController =
        rememberNavController()


    val navBackStackEntry by
    navController
        .currentBackStackEntryAsState()


    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route


    val bottomBarDestination =

        listOf(
            "home",
            "explore",
            "collection",
            "profile"
        )
            .any {
                it == currentRoute
            }


    CompositionLocalProvider(
        LocalIsEnglish provides isEnglish
    ) {

        Scaffold(

            bottomBar = {

                if (bottomBarDestination) {

                    NavigationBar {


                        NavigationBarItem(

                            icon = {

                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription =
                                        "Home"
                                )
                            },

                            label = {

                                Text(
                                    if (isEnglish)
                                        "Home"
                                    else
                                        "首页"
                                )
                            },

                            selected =
                                currentRoute == "home",

                            onClick = {

                                navController.navigate(
                                    "home"
                                ) {

                                    popUpTo("home") {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )


                        NavigationBarItem(

                            icon = {

                                Icon(
                                    Icons.Filled.Place,
                                    contentDescription =
                                        "Explore"
                                )
                            },

                            label = {

                                Text(
                                    if (isEnglish)
                                        "Explore"
                                    else
                                        "探索"
                                )
                            },

                            selected =
                                currentRoute == "explore",

                            onClick = {

                                navController.navigate(
                                    "explore"
                                ) {

                                    popUpTo("home") {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )


                        NavigationBarItem(

                            icon = {

                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription =
                                        "Collection"
                                )
                            },

                            label = {

                                Text(
                                    if (isEnglish)
                                        "Collection"
                                    else
                                        "收藏"
                                )
                            },

                            selected =
                                currentRoute ==
                                        "collection",

                            onClick = {

                                navController.navigate(
                                    "collection"
                                ) {

                                    popUpTo("home") {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )


                        NavigationBarItem(

                            icon = {

                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription =
                                        "Profile"
                                )
                            },

                            label = {

                                Text(
                                    if (isEnglish)
                                        "Profile"
                                    else
                                        "我的"
                                )
                            },

                            selected =
                                currentRoute ==
                                        "profile",

                            onClick = {

                                navController.navigate(
                                    "profile"
                                ) {

                                    popUpTo("home") {
                                        saveState = true
                                    }

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

                navController =
                    navController,

                startDestination =
                    "home",

                modifier =
                    Modifier.padding(
                        innerPadding
                    )
            ) {


                /*
                 * LANDING PAGE
                 */
                composable("home") {

                    HomeScreen()
                }


                /*
                 * EXPLORE PAGE
                 */
                composable("explore") {

                    ExploreScreen(

                        onTrailClick = {
                                trailName,
                                imageUrl ->

                            val encodedUrl =
                                URLEncoder.encode(
                                    imageUrl,
                                    StandardCharsets
                                        .UTF_8
                                        .toString()
                                )

                            navController.navigate(
                                "detail/$trailName?imageUrl=$encodedUrl"
                            )
                        }
                    )
                }


                /*
                 * COLLECTION PAGE
                 */
                composable("collection") {

                    CollectionScreen(

                        viewModel =
                            sharedCollectionViewModel,

                        onTrailClick = {
                                trailName,
                                imageUrl ->

                            val encodedUrl =
                                URLEncoder.encode(
                                    imageUrl,
                                    StandardCharsets
                                        .UTF_8
                                        .toString()
                                )

                            navController.navigate(
                                "detail/$trailName?imageUrl=$encodedUrl"
                            )
                        }
                    )
                }


                /*
                 * PROFILE PAGE
                 */
                composable("profile") {

                    ProfileScreen(

                        isEnglish =
                            isEnglish,

                        onLanguageChange = {
                            isEnglish = it
                        }
                    )
                }


                /*
                 * TRAIL DETAILS
                 */
                composable(

                    route =
                        "detail/{trailName}?imageUrl={imageUrl}",

                    arguments =
                        listOf(

                            navArgument(
                                "trailName"
                            ) {

                                type =
                                    NavType.StringType
                            },

                            navArgument(
                                "imageUrl"
                            ) {

                                type =
                                    NavType.StringType

                                defaultValue = ""
                            }
                        )

                ) { backStackEntry ->


                    val trailName =

                        backStackEntry
                            .arguments
                            ?.getString(
                                "trailName"
                            )
                            ?: "Unknown Trail"


                    val encodedUrl =

                        backStackEntry
                            .arguments
                            ?.getString(
                                "imageUrl"
                            )
                            ?: ""


                    val imageUrl =

                        URLDecoder.decode(
                            encodedUrl,
                            StandardCharsets
                                .UTF_8
                                .toString()
                        )


                    DetailScreen(

                        trailName =
                            trailName,

                        imageUrl =
                            imageUrl.ifEmpty {

                                "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?q=80&w=2560&auto=format&fit=crop"
                            },

                        onBackClick = {

                            navController
                                .popBackStack()
                        },

                        onQuizClick = {

                            navController.navigate(
                                "quiz/$trailName"
                            )
                        },

                        collectionViewModel =
                            sharedCollectionViewModel
                    )
                }


                /*
                 * LEARNING ACTIVITY / QUIZ
                 */
                composable(

                    route =
                        "quiz/{trailName}",

                    arguments =
                        listOf(

                            navArgument(
                                "trailName"
                            ) {

                                type =
                                    NavType.StringType
                            }
                        )

                ) { backStackEntry ->


                    val trailName =

                        backStackEntry
                            .arguments
                            ?.getString(
                                "trailName"
                            )
                            ?: "Trail"


                    val quizViewModel:
                            QuizViewModel =

                        viewModel(

                            factory =
                                QuizViewModelFactory(
                                    quizRepository
                                )
                        )


                    QuizScreen(

                        trailName =
                            trailName,

                        difficulty =
                            quizDifficulty,

                        viewModel =
                            quizViewModel,

                        onBackClick = {

                            navController
                                .popBackStack()
                        }
                    )
                }
            }
        }
    }
}