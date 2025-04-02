package com.nikitadev.moviefavorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.nikitadev.moviefavorites.viewmodel.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikitadev.moviefavorites.ui.theme._04_MovieFavoritesTheme
import com.nikitadev.moviefavorites.view.MovieDetailScreen
import com.nikitadev.moviefavorites.view.MovieLandscapeScreen
import com.nikitadev.moviefavorites.view.MovieListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _04_MovieFavoritesTheme {
                MovieApp()
            }
        }
    }
}

@Composable
fun MovieApp(viewModel: MovieViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val navController = rememberNavController()

    when (configuration.orientation) {
        android.content.res.Configuration.ORIENTATION_LANDSCAPE -> {
            MovieLandscapeScreen(viewModel)
        }
        else -> {
            NavHost(navController = navController, startDestination = "movieList") {
                composable("movieList") {
                    MovieListScreen(
                        viewModel = viewModel,
                        onMovieSelected = { movie ->
                            viewModel.selectMovie(movie)
                            navController.navigate("movieDetails")
                        }
                    )
                }
                composable("movieDetails") {
                    MovieDetailScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}