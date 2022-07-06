package com.garvardinho.kiko.presenter.favorites

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.favorites.FavoritesView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class FavoritesViewPresenter(private val router: Router) : MvpPresenter<FavoritesView>(), FavoritesViewDelegate {

    private val repository: Repository = RepositoryImpl(RemoteDataSource(), RealmDataSource())
    val favoritesCardViewPresenter = FavoritesCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadFavoriteMovies()
    }

    override fun loadFavoriteMovies() {
        val moviesList = repository.loadFavoriteMoviesFromRealm()
        favoritesCardViewPresenter.setMovies(moviesList)
        viewState.showFavoriteMovies(moviesList)
    }

    override fun manageFavorite(movie: MovieResultDTO) {
        if (movie.isFavorite) {
            repository.putMovieIntoRealm(movie)
        } else {
            repository.deleteMovieFromRealm(movie)
        }
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}
