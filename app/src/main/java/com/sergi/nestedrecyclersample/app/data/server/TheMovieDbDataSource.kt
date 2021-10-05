package com.sergi.nestedrecyclersample.app.data.server

import com.sergi.nestedrecyclersample.app.data.toDomainMovie
import com.sergi.nestedrecyclersample.data.remote.RemoteDataSource
import com.sergi.nestedrecyclersample.domain.Movie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, page: Int): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, "ES", page)
            .results
            .map { it.toDomainMovie() }

    override suspend fun getUpcomingMovies(apiKey: String, page: Int): List<Movie> =
        TheMovieDb.service
            .listUpcomingMoviesAsync(apiKey, "ES", page)
            .results
            .map { it.toDomainMovie() }
}