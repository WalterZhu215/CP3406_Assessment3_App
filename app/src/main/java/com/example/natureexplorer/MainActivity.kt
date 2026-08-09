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
import com.example.natureexplorer.ui.viewmodels.QuizViewModel
import com.example.natureexplorer.ui.viewmodels.QuizViewModelFactory
import com.example.natureexplorer.ui.viewmodels.StatisticsViewModel
import com.example.natureexplorer.ui.viewmodels.StatisticsViewModelFactory
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


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

    var isEnglish by
    rememberSaveable {
        mutableStateOf(true)
    }


    var quizDifficulty by
    rememberSaveable {
        mutableStateOf("Medium")
    }


    val context =
        LocalContext.current


    val database =
        androidx.compose.runtime.remember {

            TrailDatabase
                .getDatabase(context)
        }


    val trailRepository =
        androidx.compose.runtime.remember {

            TrailRepository(
                database.trailDao()
            )
        }


    val quizRepository =
        androidx.compose.runtime.remember {

            QuizRepository(
                database.quizResultDao()
            )
        }


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
            "statistics",
            "profile"
        )
            .any {
                it == currentRoute
            }


    CompositionLocalProvider(

        LocalIsEnglish provides
                isEnglish

    ) {


        Scaffold(

            bottomBar = {

                if (bottomBarDestination) {


                    NavigationBar {


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
                composable(
                    route = "home"
                ) {

                    HomeScreen()
                }


                /*
                 * EXPLORE PAGE
                 */
                composable(
                    route = "explore"
                ) {


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
                 * USER STATISTICS SCREEN
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
                 * PROFILE PAGE
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
                 * SETTINGS SCREEN
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

                                defaultValue =
                                    ""
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