package com.vivcom.data.source

import com.vivcom.domain.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun insertMovies(movies: List<Movie>)
    suspend fun getAll(): List<Movie>
    suspend fun findById(id: Int): Movie
    suspend fun update(movie: Movie)
}
