package com.example.kotlincompose

import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlincompose.model.MovieResult
import com.example.kotlincompose.network.MovieViewModel

import com.example.kotlincompose.network.NetworkService
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Call your Composable function
            APICallScreen()

        }
    }
}

// ViewModel to handle API calls


// Composable function for UI
@Composable
fun APICallScreen(viewModel: MovieViewModel = viewModel()) {
//    movies is a mutable state variable of type List<MovieResult>.
//    By using mutableStateOf, you're telling Compose that movies is a
//    value that can change over time. When the value of movies is updated,
//    any UI elements that depend on movies will be automatically recomposed
//    to reflect the changes
    var movies by remember { mutableStateOf<List<MovieResult>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    //triggered when compose is first placed
    LaunchedEffect(true) {
        // Handle button click to trigger API call
        isLoading = true
        GlobalScope.launch {
            val response = viewModel.fetchNowPlayingMovies()
            movies = response // Assuming response is a List<Movie>
            isLoading = false
        } // Replace this with your API call logic
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {

            },
            modifier = Modifier.padding(16.dp),
            enabled = !isLoading
        ) {
            // Text on the Button
            Text(text = "Make API Call")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            // Show loading indicator
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Display movies in a LazyColumn
            LazyColumn {
                items(movies) { movie ->
                    MovieListItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun MovieListItem(movie: MovieResult) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display Movie Title
            Text(
                text = movie.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Display Original Language
            Text(
                text = "Language: ${movie.original_language}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAPICallScreen() {
    APICallScreen()

}
