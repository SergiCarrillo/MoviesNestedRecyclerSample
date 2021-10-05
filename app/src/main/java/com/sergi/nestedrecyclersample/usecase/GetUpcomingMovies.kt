package com.sergi.nestedrecyclersample.usecase

import com.sergi.nestedrecyclersample.data.repository.MoviesRepository
import com.sergi.nestedrecyclersample.domain.Movie


class GetUpcomingMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(page: Int): List<Movie> = moviesRepository.getUpcomingMovies(page)
}