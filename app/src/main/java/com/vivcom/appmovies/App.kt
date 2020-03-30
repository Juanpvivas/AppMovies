package com.vivcom.appmovies

import android.app.Application
import androidx.room.Room
import com.vivcom.appmovies.model.dataBase.MovieDatabase

class App: Application() {
    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}