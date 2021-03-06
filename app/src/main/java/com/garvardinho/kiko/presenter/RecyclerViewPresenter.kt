package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.view.KikoItemView

interface RecyclerViewPresenter<V : KikoItemView> {

    fun setMovies(movies: List<MovieDTO>)
    fun bindView(view: V)
    fun getCount(): Int
    fun getMovie(position: Int): MovieDTO
}