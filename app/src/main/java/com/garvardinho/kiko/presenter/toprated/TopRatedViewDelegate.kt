package com.garvardinho.kiko.presenter.toprated

import com.garvardinho.kiko.presenter.ViewDelegate

interface TopRatedViewDelegate : ViewDelegate {

    fun loadTopRatedMovies()
    fun filterTopRatedMovies(by: Int)
}