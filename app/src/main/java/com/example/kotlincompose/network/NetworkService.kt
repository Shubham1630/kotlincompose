package com.example.kotlincompose.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class NetworkService {

    private val client = OkHttpClient()

    suspend fun getNowPlayingMovies(apiKey: String): String = withContext(Dispatchers.IO) {
        val apiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=$apiKey"

        val request = Request.Builder()
            .url(apiUrl)
            .build()

        val response = client.newCall(request).execute()

        return@withContext if (response.isSuccessful) {
            response.body?.string() ?: ""
        } else {
            "Error: ${response.code}"
        }
    }
}
