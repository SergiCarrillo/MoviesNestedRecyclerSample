package com.sergi.nestedrecyclersample.data.remote

import com.sergi.nestedrecyclersample.domain.Movie

interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, page:Int): List<Movie>
    suspend fun getUpcomingMovies(apiKey: String, page:Int): List<Movie>
}