package com.garvardinho.kiko.view.favorites

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.KikoView
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface FavoritesView : KikoView, MvpView {

    fun showFavoriteMovies(movies: List<MovieResultDTO>)
}