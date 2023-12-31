package com.example.kotlincompose.model

data class MovieApiResponse(
    val dates: Dates,
    val page: Int,
    val results: List<MovieResult>
)

data class Dates(
    val maximum: String,
    val minimum: String
)

data class MovieResult(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val MoviesDetailsScreen: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)