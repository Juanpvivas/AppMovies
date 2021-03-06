package com.vivcom.appmovies.model.server

import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieApi {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieDbResult
}