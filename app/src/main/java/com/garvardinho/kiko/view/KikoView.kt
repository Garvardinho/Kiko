package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.retrofit.MovieDTO
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface KikoView : MvpView {

    fun manageFavorite(movie: MovieDTO)
    fun showError(error: String = "Check your Internet connection")
}