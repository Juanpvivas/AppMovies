package com.vivcom.appmovies.di

import android.app.Application
import com.vivcom.appmovies.ui.detail.DetailActivityComponent
import com.vivcom.appmovies.ui.detail.DetailActivityModule
import com.vivcom.appmovies.ui.main.MainActivityComponent
import com.vivcom.appmovies.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyMoviesComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule) : DetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}