package com.sergi.nestedrecyclersample.app.data.server

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("movie/popular")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int): MovieDbResult

    @GET("movie/upcoming")
    suspend fun listUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("page") page: Int): MovieDbResult
}
