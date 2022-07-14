package com.garvardinho.kiko.view.toprated

import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.view.KikoView
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface TopRatedView : KikoView, MvpView {

    fun showTopRatedMovies(movies: List<MovieDTO>)
    fun showTopRatedLoading()
    fun filterTopRatedMovies()
}