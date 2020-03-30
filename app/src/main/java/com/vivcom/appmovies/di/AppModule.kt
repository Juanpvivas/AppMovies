package com.vivcom.appmovies.di

import android.app.Application
import androidx.room.Room
import com.vivcom.appmovies.R
import com.vivcom.appmovies.data.AndroidPermissionChecker
import com.vivcom.appmovies.data.PlayServicesLocationDataSource
import com.vivcom.appmovies.data.dataBase.MovieDatabase
import com.vivcom.appmovies.data.dataBase.RoomDataSource
import com.vivcom.appmovies.data.server.TheMovieDataSource
import com.vivcom.data.repository.LocationDataSource
import com.vivcom.data.repository.PermissionChecker
import com.vivcom.data.source.LocalDataSource
import com.vivcom.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        MovieDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: MovieDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = TheMovieDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}