package com.vivcom.usecases

import com.vivcom.data.repository.MoviesRepository
import com.vivcom.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}