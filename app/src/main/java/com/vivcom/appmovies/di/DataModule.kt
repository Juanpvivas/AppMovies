package com.vivcom.appmovies.di

import com.vivcom.data.repository.LocationDataSource
import com.vivcom.data.repository.MoviesRepository
import com.vivcom.data.repository.PermissionChecker
import com.vivcom.data.repository.RegionRepository
import com.vivcom.data.source.LocalDataSource
import com.vivcom.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = MoviesRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
}