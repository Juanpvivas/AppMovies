package com.vivcom.usecases

import com.vivcom.data.repository.MoviesRepository

class GetPopularMovies(private val repository: MoviesRepository) {
    suspend fun invoke() = repository.findPopularMovies()
}
