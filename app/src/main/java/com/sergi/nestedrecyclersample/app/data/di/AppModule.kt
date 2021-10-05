package com.sergi.nestedrecyclersample.app.data.di

import android.app.Application
import com.sergi.nestedrecyclersample.R
import com.sergi.nestedrecyclersample.app.data.server.TheMovieDbDataSource
import com.sergi.nestedrecyclersample.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDbDataSource()

}