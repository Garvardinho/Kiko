package com.garvardinho.kiko.view.home

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.KikoView
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface HomeView : KikoView, MvpView {

    fun showNowPlayingMovies(movies: List<MovieResultDTO>)
    fun showUpcomingMovies(movies: List<MovieResultDTO>)
    fun showNowPlayingLoading(loading: Boolean)
    fun showUpcomingLoading(loading: Boolean)
}
