package com.nikitadev.moviefavorites.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double
) : Serializable {
    val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500${posterPath ?: ""}"

    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
}