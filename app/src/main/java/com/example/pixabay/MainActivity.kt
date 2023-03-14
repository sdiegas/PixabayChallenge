package com.example.pixabay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pixabay.ui.search.PixabaySearchResults
import com.example.pixabay.ui.detail.DetailScreen
import com.example.pixabay.ui.theme.PixabayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixabayTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "search"
                ) {
                    composable("search") {
                        PixabaySearchResults(navController = navController)
                    }
                    composable(
                        route = "detail/{name}/{tags}/{likes}/{downloads}/{comments}?imageUrl={imageUrl}",
                        arguments = listOf(
                            navArgument("imageUrl") {
                                type = NavType.StringType
                            },
                            navArgument("name") {
                                type = NavType.StringType
                            },
                            navArgument("tags") {
                                type = NavType.StringType
                            },
                            navArgument("likes") {
                                type = NavType.IntType
                            },
                            navArgument("downloads") {
                                type = NavType.IntType
                            },
                            navArgument("comments") {
                                type = NavType.IntType
                            },
                        )
                    ) {
                        val imageUrl = it.arguments?.getString("imageUrl") ?: ""
                        val name = it.arguments?.getString("name") ?: ""
                        val tags = it.arguments?.getString("tags") ?: ""
                        val likes = it.arguments?.getInt("likes") ?: 0
                        val downloads = it.arguments?.getInt("downloads") ?: 0
                        val comments = it.arguments?.getInt("comments") ?: 0

                        DetailScreen(
                            imageUrl = imageUrl,
                            name = name,
                            tags = tags,
                            likes = likes,
                            downloads = downloads,
                            comments = comments
                        )
                    }
                }
            }
        }
    }
}

