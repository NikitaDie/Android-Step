package com.nikitadev.moviefavorites.model.repository

import com.nikitadev.moviefavorites.model.data.Movie
import com.nikitadev.moviefavorites.model.network.RetrofitClient

class MovieRepository {
    suspend fun getPopularMovies(apiKey: String): List<Movie> {
        return RetrofitClient.movieApiService.getPopularMovies(apiKey).results
    }
}