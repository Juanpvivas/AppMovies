package com.vivcom.appmovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vivcom.appmovies.ui.common.ScopedViewModel
import com.vivcom.domain.Movie
import com.vivcom.usecases.FindMovieById
import com.vivcom.usecases.ToggleMovieFavorite
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int,
                      private val findMovieById: FindMovieById,
                      private val toggleMovieFavorite: ToggleMovieFavorite
) :
    ScopedViewModel() {

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(findMovieById.invoke(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            val updatedMovie = it.copy(favorite = !it.favorite)
            _model.value = UiModel(updatedMovie)
            toggleMovieFavorite.invoke(updatedMovie)
        }
    }
}