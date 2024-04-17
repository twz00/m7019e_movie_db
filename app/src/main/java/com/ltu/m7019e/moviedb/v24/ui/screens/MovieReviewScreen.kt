package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ltu.m7019e.moviedb.v24.model.Movie

@Composable
fun MovieReviewScreen(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = "Reviews for " + movie.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}