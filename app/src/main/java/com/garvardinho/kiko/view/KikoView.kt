package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.MovieResultDTO
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface KikoView : MvpView {

    fun manageFavorite(movie: MovieResultDTO)
    fun showError(error: String = "Check your Internet connection")
}