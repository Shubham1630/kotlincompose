package com.example.kotlincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.runtime.getValue

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kotlincompose.moviesViewmodel.MovieViewModel

import com.example.kotlincompose.ui.theme.movieslist.MoviesListScreen


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
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    MoviesListScreen(viewModel)
}


@Preview(showBackground = true)
@Composable
fun PreviewAPICallScreen() {
    APICallScreen()

}
