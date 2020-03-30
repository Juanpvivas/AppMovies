package com.vivcom.appmovies.data.dataBase

import com.vivcom.appmovies.data.toDomainMovie
import com.vivcom.appmovies.data.toRoomMovie
import com.vivcom.data.source.LocalDataSource
import com.vivcom.domain.Movie as DomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: MovieDatabase) : LocalDataSource {

    private val dao = db.movieDao()

    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) { dao.movieCount() <= 0 }


    override suspend fun insertMovies(movies: List<DomainMovie>) {
        withContext(Dispatchers.IO){
            dao.insertMovies(movies.map { it.toRoomMovie() })
        }
    }

    override suspend fun getAll(): List<DomainMovie> = withContext(Dispatchers.IO){
        dao.getAll().map { it.toDomainMovie() }
    }

    override suspend fun findById(id: Int): DomainMovie = withContext(Dispatchers.IO) {
        dao.findById(id).toDomainMovie()
    }

    override suspend fun update(movie: DomainMovie) {
        withContext(Dispatchers.IO) { dao.updateMovie(movie.toRoomMovie()) }
    }
}
