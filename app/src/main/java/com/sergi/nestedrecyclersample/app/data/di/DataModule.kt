package com.sergi.nestedrecyclersample.app.data.di

import com.sergi.nestedrecyclersample.data.remote.RemoteDataSource
import com.sergi.nestedrecyclersample.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun moviesRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(remoteDataSource, apiKey)

}