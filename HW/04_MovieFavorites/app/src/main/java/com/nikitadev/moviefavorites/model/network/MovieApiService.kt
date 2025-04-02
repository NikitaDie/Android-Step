package com.nikitadev.moviefavorites.model.network

import com.nikitadev.moviefavorites.model.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): MovieResponse

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}