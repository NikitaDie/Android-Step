package com.nikitadev.moviefavorites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitadev.moviefavorites.BuildConfig
import com.nikitadev.moviefavorites.model.data.Movie
import com.nikitadev.moviefavorites.model.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieViewModel : ViewModel() {

    data class MoviesUiState(
        val movies: List<Movie> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val isLoadingMore: Boolean = false,
        val hasReachedEnd: Boolean = false
    )

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    private val apiKey = BuildConfig.TMDB_API_KEY;
    private var currentPage = 1

    init {
        fetchMovies()
    }

    private fun fetchMovies(page: Int = 1) {
        viewModelScope.launch {
            val isFirstPage = (page == 1)

            _uiState.update {
                it.copy(
                    isLoading = isFirstPage,
                    isLoadingMore = !isFirstPage,
                    error = null
                )
            }
            try {
                val response = RetrofitClient.movieApiService.getPopularMovies(apiKey, page)
                val newMovies = response.results.filter { it.posterPath != null }

                _uiState.update { currentState ->
                    currentState.copy(
                        movies = if (isFirstPage) newMovies else currentState.movies + newMovies,
                        isLoading = false,
                        isLoadingMore = false,
                        hasReachedEnd = newMovies.isEmpty()
                    )
                }

                currentPage = page
            } catch (e: IOException) {
                handleFetchError("Network error occurred. Please check your connection.", e)
            } catch (e: HttpException) {
                handleFetchError("Server error: ${e.code()}", e)
            } catch (e: Exception) {
                handleFetchError("An unexpected error occurred: ${e.message}", e)
            }
        }
    }

    private fun handleFetchError(message: String, exception: Exception) {
        Log.e("MovieViewModel", message, exception)
        _uiState.update {
            it.copy(
                isLoading = false,
                isLoadingMore = false,
                error = message
            )
        }
    }

    fun loadMoreMovies() {
        if (!_uiState.value.isLoading && !_uiState.value.isLoadingMore && !_uiState.value.hasReachedEnd) {
            fetchMovies(currentPage + 1)
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}