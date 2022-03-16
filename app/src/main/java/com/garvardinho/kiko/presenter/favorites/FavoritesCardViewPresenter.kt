package com.garvardinho.kiko.presenter.favorites

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.CardViewPresenter
import com.garvardinho.kiko.view.KikoCardView

class FavoritesCardViewPresenter : CardViewPresenter {

    private val movies = mutableListOf<MovieResultDTO>()

    override fun bindView(view: KikoCardView) {
        val movie = movies[view.pos]
        view.setImage(movie.poster_path ?: "")
        view.setTitle(movie.title)
        view.setDate(movie.release_date ?: "")
        view.setFavorite(movie)
        view.setRating(movie.vote_average)
        view.setOverview(movie.overview ?: "")
    }

    override fun getCount(): Int = movies.size

    override fun setMovies(movies: List<MovieResultDTO>) {
        this.movies.addAll(movies)
    }

    override fun getMovie(position: Int): MovieResultDTO = movies[position]
}