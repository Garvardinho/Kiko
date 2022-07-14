package com.garvardinho.kiko.view.home

import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.view.KikoView
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface HomeView : KikoView, MvpView {

    fun showNowPlayingMovies(movies: List<MovieDTO>)
    fun showUpcomingMovies(movies: List<MovieDTO>)
    fun showNowPlayingLoading(loading: Boolean)
    fun showUpcomingLoading(loading: Boolean)
    fun filterNowPlayingMovies()
    fun filterUpcomingMovies()
}
