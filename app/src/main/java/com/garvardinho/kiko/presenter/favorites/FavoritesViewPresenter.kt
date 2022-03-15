package com.garvardinho.kiko.presenter.favorites

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RealmDataSource
import com.garvardinho.kiko.view.favorites.FavoritesView
import moxy.MvpPresenter

class FavoritesViewPresenter : MvpPresenter<FavoritesView>(), FavoritesViewDelegate {

    private val repositoryRemote: Repository = RepositoryImpl(RealmDataSource())
    private val repositoryRealm: Repository = RepositoryImpl(RealmDataSource())
    val favoritesCardViewPresenter = FavoritesCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadFavoriteMovies()
    }

    override fun loadFavoriteMovies() {
        val moviesList = repositoryRemote.loadFavoriteMoviesFromRealm()
        favoritesCardViewPresenter.setMovies(moviesList)
        viewState.showFavoriteMovies(moviesList)
    }

    override fun manageFavorite(movie: MovieResultDTO) {
        if (movie.isFavorite) {
            repositoryRealm.putMovieIntoRealm(movie)
        } else {
            repositoryRealm.deleteMovieFromRealm(movie)
        }
    }
}
