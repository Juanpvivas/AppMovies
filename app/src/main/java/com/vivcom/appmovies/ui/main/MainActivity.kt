package com.vivcom.appmovies.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vivcom.appmovies.ui.common.PermissionRequester
import com.vivcom.appmovies.R
import com.vivcom.appmovies.databinding.ActivityMainBinding
import com.vivcom.appmovies.data.AndroidPermissionChecker
import com.vivcom.appmovies.data.PlayServicesLocationDataSource
import com.vivcom.appmovies.data.dataBase.RoomDataSource
import com.vivcom.appmovies.data.server.TheMovieDataSource
import com.vivcom.appmovies.ui.common.app
import com.vivcom.appmovies.ui.common.getViewModel
import com.vivcom.appmovies.ui.common.startActivity
import com.vivcom.appmovies.ui.detail.DetailActivity
import com.vivcom.data.repository.MoviesRepository
import com.vivcom.data.repository.RegionRepository
import com.vivcom.usecases.GetPopularMovies

class MainActivity : AppCompatActivity() {

    private lateinit var component: MainActivityComponent
    private val viewModel: MainViewModel by lazy { getViewModel { component.mainViewModel } }
    private lateinit var adapter: MoviesAdapter
    private lateinit var binding: ActivityMainBinding
    private val coarsePermissionRequester =
        PermissionRequester(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        component = app.component.plus(MainActivityModule())

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        binding.progress.visibility =
            if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.movies = model.movies
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            MainViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }
}
