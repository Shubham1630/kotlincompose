package com.example.kotlincompose.ui.theme.movieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlincompose.model.MovieResult
import com.example.kotlincompose.moviesViewmodel.MovieViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun MoviesListScreen(onMovieItemClick: (MovieResult) -> Unit) {
    //    movies is a mutable state variable of type List<MovieResult>.
//    By using mutableStateOf, you're telling Compose that movies is a
//    value that can change over time. When the value of movies is updated,
//    any UI elements that depend on movies will be automatically recomposed
//    to reflect the changes
    val viewModel: MovieViewModel = viewModel()
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
                    MovieListItem(movie = movie, onMovieItemClick)
                }
            }
        }


    }


}

@Composable
fun MovieListItem(movie: MovieResult, onMovieItemClick: (MovieResult) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onMovieItemClick(movie) },
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
