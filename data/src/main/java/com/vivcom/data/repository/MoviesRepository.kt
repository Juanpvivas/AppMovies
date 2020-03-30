package com.vivcom.data.repository

import com.vivcom.data.source.LocalDataSource
import com.vivcom.data.source.RemoteDataSource
import com.vivcom.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val apiKey: String
) {
    suspend fun findPopularMovies(): List<Movie> {

        if (localDataSource.isEmpty()) {

            val movies = remoteDataSource
                .getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.insertMovies(movies)
        }

        return localDataSource.getAll()

    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)

    suspend fun update(movie: Movie) = localDataSource.update(movie)
}


