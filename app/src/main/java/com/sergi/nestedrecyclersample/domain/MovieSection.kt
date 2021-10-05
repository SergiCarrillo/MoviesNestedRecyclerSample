package com.sergi.nestedrecyclersample.domain

import java.util.*

data class MovieSection(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val movies: List<Movie> = mutableListOf(),
    val isHorizontal : Boolean = true
)