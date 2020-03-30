package com.vivcom.appmovies.data.server

import com.vivcom.appmovies.data.toDomainMovie
import com.vivcom.data.source.RemoteDataSource
import com.vivcom.domain.Movie

class TheMovieDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        RetrofitBuild.service.listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}