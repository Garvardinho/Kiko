package com.garvardinho.kiko.presenter.favorites

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.presenter.CardViewPresenter
import com.garvardinho.kiko.view.KikoCardView
import com.garvardinho.kiko.view.home.SORT_BY_DATE
import com.garvardinho.kiko.view.home.SORT_BY_RATING
import com.garvardinho.kiko.view.home.SORT_BY_TITLE

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

    override fun sort(by: Int) {
        when (by) {
            SORT_BY_RATING -> {
                movies.sortBy { movie ->
                    -movie.vote_average
                }
            }
            SORT_BY_DATE -> {
                movies.sortBy { movie ->
                    movie.release_date
                }
            }
            SORT_BY_TITLE -> {
                movies.sortBy { movie ->
                    movie.title
                }
            }
        }
    }

    override fun setMovies(movies: List<MovieResultDTO>) {
        this.movies.addAll(movies)
    }

    override fun getMovie(position: Int): MovieResultDTO = movies[position]
}