package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RealmDataSource
import com.garvardinho.kiko.view.FavoritesView

class FavoritesViewPresenter(private val view : FavoritesView) : FavoritesViewDelegate {
    private val repositoryRemote: Repository = RepositoryImpl(RealmDataSource())
    private val repositoryRealm: Repository = RepositoryImpl(RealmDataSource())

    override fun loadFavoriteMovies() {
        view.showFavoriteMovies(repositoryRemote.loadFavoriteMoviesFromRealm())
    }

    override fun manageFavorite(movie: MovieResultDTO) {
        if (movie.isFavorite) {
            repositoryRealm.putMovieIntoRealm(movie)
        } else {
            repositoryRealm.deleteMovieFromRealm(movie)
        }
    }
}