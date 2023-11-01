package com.example.kotlincompose.moviesViewmodel

import androidx.lifecycle.ViewModel
import com.example.kotlincompose.model.MovieApiResponse
import com.example.kotlincompose.model.MovieResult
import com.example.kotlincompose.network.NetworkService

import com.google.gson.Gson


class MovieViewModel : ViewModel() {
    private val apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
    private val networkService = NetworkService()

    suspend fun fetchNowPlayingMovies(): List<MovieResult> {
        val response = networkService.getNowPlayingMovies(apiKey)
        return parseApiResponse(response)
    }

    private fun parseApiResponse(response: String): List<MovieResult> {
        val movieApiResponse = Gson().fromJson(response, MovieApiResponse::class.java)
        return movieApiResponse.results
    }
}