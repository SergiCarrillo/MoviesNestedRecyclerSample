package com.sergi.nestedrecyclersample.data.repository

import com.sergi.nestedrecyclersample.data.remote.RemoteDataSource
import com.sergi.nestedrecyclersample.domain.Movie

class MoviesRepository(
    private val remoteDataSource: RemoteDataSource, private val apiKey: String) {

    suspend fun getPopularMovies(page : Int): List<Movie> {
        return remoteDataSource.getPopularMovies(apiKey, page)
    }

    suspend fun getUpcomingMovies(page : Int): List<Movie> {
        return remoteDataSource.getUpcomingMovies(apiKey, page)
    }

}