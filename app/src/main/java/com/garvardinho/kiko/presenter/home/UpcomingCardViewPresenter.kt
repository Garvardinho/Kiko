package com.garvardinho.kiko.presenter.home

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.CardViewPresenter
import com.garvardinho.kiko.view.KikoCardView

class UpcomingCardViewPresenter : CardViewPresenter {

    private val movies = mutableListOf<MovieResultDTO>()

    override fun bindView(view: KikoCardView) {
        val movie = movies[view.pos]
        view.setImage(movie.poster_path ?: "")
        view.setTitle(movie.title)
        view.setDate(movie.release_date ?: "")
        view.setFavorite(movie)
    }

    override fun getCount(): Int = movies.size

    override fun setMovies(movies: List<MovieResultDTO>) {
        this.movies.addAll(movies)
    }

    override fun getMovie(position: Int): MovieResultDTO = movies[position]
}