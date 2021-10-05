package com.sergi.nestedrecyclersample.app.ui.main

import com.sergi.nestedrecyclersample.data.repository.MoviesRepository
import com.sergi.nestedrecyclersample.usecase.GetPopularMovies
import com.sergi.nestedrecyclersample.usecase.GetUpcomingMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MainActivityModule {

    @Provides
    @ViewModelScoped
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)

    @Provides
    @ViewModelScoped
    fun getUpcomingMoviesProvider(moviesRepository: MoviesRepository) =
        GetUpcomingMovies(moviesRepository)
}