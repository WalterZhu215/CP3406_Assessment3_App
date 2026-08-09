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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.natureexplorer.data.WeatherRepository

import com.example.natureexplorer.network.WeatherApiClient

import com.example.natureexplorer.ui.screens.CollectionScreen
import com.example.natureexplorer.ui.screens.DetailScreen
import com.example.natureexplorer.ui.screens.ExploreScreen
import com.example.natureexplorer.ui.screens.HomeScreen
import com.example.natureexplorer.ui.screens.ProfileScreen
import com.example.natureexplorer.ui.screens.QuizScreen
import com.example.natureexplorer.ui.screens.SettingsScreen
import com.example.natureexplorer.ui.screens.StatisticsScreen

import com.example.natureexplorer.ui.theme.NatureExplorerTheme

import com.example.natureexplorer.ui.viewmodels.CollectionViewModel
import com.example.natureexplorer.ui.viewmodels.CollectionViewModelFactory
import com.example.natureexplorer.ui.viewmodels.HomeViewModel
import com.example.natureexplorer.ui.viewmodels.HomeViewModelFactory
import com.example.natureexplorer.ui.viewmodels.QuizViewModel
import com.example.natureexplorer.ui.viewmodels.QuizViewModelFactory
import com.example.natureexplorer.ui.viewmodels.StatisticsViewModel
import com.example.natureexplorer.ui.viewmodels.StatisticsViewModelFactory

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


/*
 * Makes the selected language available
 * throughout the Compose UI.
 */
val LocalIsEnglish =
    compositionLocalOf {
        true
    }


class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

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

    /*
     * Global language preference.
     *
     * true  = English
     * false = Chinese
     */
    var isEnglish by
    rememberSaveable {
        mutableStateOf(true)
    }


    /*
     * Global quiz difficulty.
     *
     * This value is controlled by
     * SettingsScreen.
     */
    var quizDifficulty by
    rememberSaveable {
        mutableStateOf("Medium")
    }


    /*
     * Android context.
     */
    val context =
        LocalContext.current


    /*
     * -------------------------------------------------
     * ROOM DATABASE
     * -------------------------------------------------
     */
    val database =
        remember {

            TrailDatabase
                .getDatabase(context)
        }


    /*
     * -------------------------------------------------
     * REPOSITORIES
     * -------------------------------------------------
     */

    /*
     * Handles saved trail data.
     */
    val trailRepository =
        remember {

            TrailRepository(
                database.trailDao()
            )
        }


    /*
     * Handles quiz result data.
     */
    val quizRepository =
        remember {

            QuizRepository(
                database.quizResultDao()
            )
        }


    /*
     * Handles live weather/environmental data.
     */
    val weatherRepository =
        remember {

            WeatherRepository(
                WeatherApiClient.apiService
            )
        }


    /*
     * -------------------------------------------------
     * SHARED VIEWMODELS
     * -------------------------------------------------
     */

    /*
     * Collection ViewModel is shared because
     * DetailScreen and CollectionScreen both
     * access saved trail information.
     */
    val sharedCollectionViewModel:
            CollectionViewModel =

        viewModel(

            factory =
                CollectionViewModelFactory(
                    trailRepository
                )
        )


    /*
     * Home ViewModel connects:
     *
     * HomeScreen
     *      ↓
     * HomeViewModel
     *      ↓
     * WeatherRepository
     *      ↓
     * Open-Meteo API
     */
    val homeViewModel:
            HomeViewModel =

        viewModel(

            factory =
                HomeViewModelFactory(
                    weatherRepository
                )
        )


    /*
     * -------------------------------------------------
     * NAVIGATION
     * -------------------------------------------------
     */
    val navController =
        rememberNavController()


    val navBackStackEntry by
    navController
        .currentBackStackEntryAsState()


    val currentRoute =
        navBackStackEntry
            ?.destination
            ?.route


    /*
     * Bottom navigation is displayed only
     * on the five main pages.
     */
    val bottomBarDestination =

        listOf(
            "home",
            "explore",
            "collection",
            "statistics",
            "profile"
        )
            .any {
                it == currentRoute
            }


    /*
     * Provides the language preference
     * to every screen.
     */
    CompositionLocalProvider(

        LocalIsEnglish provides
                isEnglish

    ) {


        Scaffold(

            /*
             * -------------------------------------------------
             * BOTTOM NAVIGATION
             * -------------------------------------------------
             */
            bottomBar = {

                if (bottomBarDestination) {


                    NavigationBar {


                        /*
                         * HOME
                         */
                        NavigationBarItem(

                            icon = {

                                Icon(
                                    imageVector =
                                        Icons.Filled.Home,

                                    contentDescription =
                                        "Home"
                                )
                            },

                            label = {

                                Text(
                                    text =
                                        if (isEnglish)
                                            "Home"
                                        else
                                            "首页"
                                )
                            },

                            selected =
                                currentRoute ==
                                        "home",

                            onClick = {

                                navController.navigate(
                                    "home"
                                ) {

                                    popUpTo(
                                        "home"
                                    ) {

                                        saveState =
                                            true
                                    }

                                    launchSingleTop =
                                        true

                                    restoreState =
                                        true
                                }
                            }
                        )


                        /*
                         * EXPLORE
                         */
                        NavigationBarItem(

                            icon = {

                                Icon(
                                    imageVector =
                                        Icons.Filled.Place,

                                    contentDescription =
                                        "Explore"
                                )
                            },

                            label = {

                                Text(
                                    text =
                                        if (isEnglish)
                                            "Explore"
                                        else
                                            "探索"
                                )
                            },

                            selected =
                                currentRoute ==
                                        "explore",

                            onClick = {

                                navController.navigate(
                                    "explore"
                                ) {

                                    popUpTo(
                                        "home"
                                    ) {

                                        saveState =
                                            true
                                    }

                                    launchSingleTop =
                                        true

                                    restoreState =
                                        true
                                }
                            }
                        )


                        /*
                         * COLLECTION
                         */
                        NavigationBarItem(

                            icon = {

                                Icon(
                                    imageVector =
                                        Icons.Filled.Favorite,

                                    contentDescription =
                                        "Collection"
                                )
                            },

                            label = {

                                Text(
                                    text =
                                        if (isEnglish)
                                            "Saved"
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

                                    popUpTo(
                                        "home"
                                    ) {

                                        saveState =
                                            true
                                    }

                                    launchSingleTop =
                                        true

                                    restoreState =
                                        true
                                }
                            }
                        )


                        /*
                         * STATISTICS
                         */
                        NavigationBarItem(

                            icon = {

                                Icon(
                                    imageVector =
                                        Icons.Filled.Star,

                                    contentDescription =
                                        "Statistics"
                                )
                            },

                            label = {

                                Text(
                                    text =
                                        if (isEnglish)
                                            "Stats"
                                        else
                                            "统计"
                                )
                            },

                            selected =
                                currentRoute ==
                                        "statistics",

                            onClick = {

                                navController.navigate(
                                    "statistics"
                                ) {

                                    popUpTo(
                                        "home"
                                    ) {

                                        saveState =
                                            true
                                    }

                                    launchSingleTop =
                                        true

                                    restoreState =
                                        true
                                }
                            }
                        )


                        /*
                         * PROFILE
                         */
                        NavigationBarItem(

                            icon = {

                                Icon(
                                    imageVector =
                                        Icons.Filled.Person,

                                    contentDescription =
                                        "Profile"
                                )
                            },

                            label = {

                                Text(
                                    text =
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

                                    popUpTo(
                                        "home"
                                    ) {

                                        saveState =
                                            true
                                    }

                                    launchSingleTop =
                                        true

                                    restoreState =
                                        true
                                }
                            }
                        )
                    }
                }
            }

        ) { innerPadding ->


            /*
             * -------------------------------------------------
             * NAVIGATION HOST
             * -------------------------------------------------
             */
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
                 * =================================================
                 * LANDING PAGE / HOME
                 * =================================================
                 *
                 * Includes live environmental information
                 * from Open-Meteo.
                 */
                composable(
                    route = "home"
                ) {


                    HomeScreen(

                        viewModel =
                            homeViewModel
                    )
                }


                /*
                 * =================================================
                 * EXPLORE SCREEN
                 * =================================================
                 */
                composable(
                    route = "explore"
                ) {


                    ExploreScreen(

                        onTrailClick = {
                                trailName,
                                imageUrl ->


                            /*
                             * Encode the URL before placing
                             * it inside a navigation route.
                             */
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
                 * =================================================
                 * COLLECTION SCREEN
                 * =================================================
                 */
                composable(
                    route = "collection"
                ) {


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
                 * =================================================
                 * USER STATISTICS SCREEN
                 * =================================================
                 *
                 * Reads real quiz results and saved trails
                 * from Room.
                 */
                composable(
                    route = "statistics"
                ) {


                    val statisticsViewModel:
                            StatisticsViewModel =

                        viewModel(

                            factory =
                                StatisticsViewModelFactory(

                                    quizRepository =
                                        quizRepository,

                                    trailRepository =
                                        trailRepository
                                )
                        )


                    StatisticsScreen(

                        viewModel =
                            statisticsViewModel
                    )
                }


                /*
                 * =================================================
                 * PROFILE SCREEN
                 * =================================================
                 */
                composable(
                    route = "profile"
                ) {


                    ProfileScreen(

                        isEnglish =
                            isEnglish,

                        onLanguageChange = {

                            isEnglish =
                                it
                        },

                        onSettingsClick = {

                            navController.navigate(
                                "settings"
                            )
                        }
                    )
                }


                /*
                 * =================================================
                 * SETTINGS SCREEN
                 * =================================================
                 *
                 * Controls:
                 *
                 * - Language
                 * - Quiz difficulty
                 */
                composable(
                    route = "settings"
                ) {


                    SettingsScreen(

                        isEnglish =
                            isEnglish,

                        quizDifficulty =
                            quizDifficulty,

                        onLanguageChange = {

                            isEnglish =
                                it
                        },

                        onDifficultyChange = {

                            quizDifficulty =
                                it
                        },

                        onBackClick = {

                            navController
                                .popBackStack()
                        }
                    )
                }


                /*
                 * =================================================
                 * TRAIL DETAIL SCREEN
                 * =================================================
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

                                defaultValue =
                                    ""
                            }
                        )

                ) { backStackEntry ->


                    /*
                     * Get the selected trail name.
                     */
                    val trailName =

                        backStackEntry
                            .arguments
                            ?.getString(
                                "trailName"
                            )
                            ?: "Unknown Trail"


                    /*
                     * Get the encoded image URL.
                     */
                    val encodedUrl =

                        backStackEntry
                            .arguments
                            ?.getString(
                                "imageUrl"
                            )
                            ?: ""


                    /*
                     * Decode the image URL.
                     */
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
                 * =================================================
                 * LEARNING ACTIVITY / QUIZ
                 * =================================================
                 *
                 * Quiz results are saved into Room.
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


                    /*
                     * Get trail name from navigation.
                     */
                    val trailName =

                        backStackEntry
                            .arguments
                            ?.getString(
                                "trailName"
                            )
                            ?: "Trail"


                    /*
                     * Each Quiz screen receives a
                     * QuizViewModel connected to the
                     * QuizRepository.
                     */
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