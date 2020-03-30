package com.vivcom.appmovies

import android.app.Application
import androidx.room.Room
import com.vivcom.appmovies.data.dataBase.MovieDatabase
import com.vivcom.appmovies.di.DaggerMyMoviesComponent
import com.vivcom.appmovies.di.MyMoviesComponent

class App: Application() {
    lateinit var component: MyMoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = DaggerMyMoviesComponent
            .factory()
            .create(this)
    }
}