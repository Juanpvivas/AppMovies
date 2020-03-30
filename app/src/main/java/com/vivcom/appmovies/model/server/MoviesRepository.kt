package com.vivcom.appmovies.model.server

import com.vivcom.appmovies.App
import com.vivcom.appmovies.R
import com.vivcom.appmovies.model.RegionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.vivcom.appmovies.model.dataBase.Movie as DbMovie
import com.vivcom.appmovies.model.server.Movie as ServerMovie

class MoviesRepository(application: App) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository =
        RegionRepository(application)
    private val db = application.db

    suspend fun findPopularMovies(): List<DbMovie> = withContext(Dispatchers.IO) {

        with(db.movieDao()) {
            if (movieCount() <= 0) {

                val movies = RetrofitBuild.service
                    .listPopularMoviesAsync(apiKey, regionRepository.findLastRegion())
                    .results

                insertMovies(movies.map(ServerMovie::convertToDbMovie))
            }

            getAll()
        }
    }

    suspend fun findById(id: Int): DbMovie = withContext(Dispatchers.IO) {
        db.movieDao().findById(id)
    }

    suspend fun update(movie: DbMovie) = withContext(Dispatchers.IO) {
        db.movieDao().updateMovie(movie)
    }
}

private fun ServerMovie.convertToDbMovie() = DbMovie(
    0,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: posterPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)