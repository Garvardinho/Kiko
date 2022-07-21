package com.garvardinho.kiko.presenter.favorites

import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.favorites.FavoritesView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class FavoritesViewPresenter : MvpPresenter<FavoritesView>(), FavoritesViewDelegate {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repository: Repository
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

    override fun filterFavoriteMovies(by: Int) {
        favoritesCardViewPresenter.sort(by)
        viewState.filterFavoriteMovies()
    }

    override fun manageFavorite(movie: MovieDTO) {
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
