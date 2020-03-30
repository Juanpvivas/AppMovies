package com.vivcom.appmovies.ui.main

import com.vivcom.data.repository.MoviesRepository
import com.vivcom.usecases.GetPopularMovies
import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(getPopularMovies: GetPopularMovies) = MainViewModel(getPopularMovies)

    @Provides
    fun getPopularMoviesProvider(moviesRepository: MoviesRepository) =
        GetPopularMovies(moviesRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}