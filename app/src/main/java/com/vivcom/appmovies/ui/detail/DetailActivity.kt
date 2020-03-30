package com.vivcom.appmovies.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.vivcom.appmovies.R
import com.vivcom.appmovies.databinding.ActivityDetailBinding
import com.vivcom.appmovies.data.AndroidPermissionChecker
import com.vivcom.appmovies.data.PlayServicesLocationDataSource
import com.vivcom.appmovies.data.dataBase.RoomDataSource
import com.vivcom.appmovies.data.server.TheMovieDataSource
import com.vivcom.appmovies.ui.common.app
import com.vivcom.appmovies.ui.common.getViewModel
import com.vivcom.appmovies.ui.common.loadUrl
import com.vivcom.data.repository.MoviesRepository
import com.vivcom.data.repository.RegionRepository
import com.vivcom.usecases.FindMovieById
import com.vivcom.usecases.ToggleMovieFavorite

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private lateinit var component: DetailActivityComponent
    private val viewModel by lazy { getViewModel { component.detailViewModel } }
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        component = app.component.plus(DetailActivityModule(intent.getIntExtra(MOVIE, -1)))

        viewModel.model.observe(this, Observer(::updateUi))

        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
        binding.movieDetailToolbar.title = title
        binding.movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        binding.movieDetailSummary.text = overview
        binding.movieDetailInfo.setMovie(this)

        val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        binding.movieDetailFavorite.setImageDrawable(getDrawable(icon))
    }
}
