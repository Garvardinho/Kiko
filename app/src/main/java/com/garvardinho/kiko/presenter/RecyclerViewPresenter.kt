package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.view.KikoItemView

interface RecyclerViewPresenter<V : KikoItemView> {

    fun setMovies(movies: List<MovieResultDTO>)
    fun bindView(view: V)
    fun getCount(): Int
    fun getMovie(position: Int): MovieResultDTO
}