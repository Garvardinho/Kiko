package com.garvardinho.kiko.presenter.details

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.presenter.ViewDelegate
import com.garvardinho.kiko.view.details.DetailsView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MovieDetailsViewPresenter(private val router: Router) : MvpPresenter<DetailsView>(), ViewDelegate {

    private val repositoryRealm: Repository = RepositoryImpl(RemoteDataSource(), RealmDataSource())

    override fun manageFavorite(movie: MovieResultDTO) {
        if (movie.isFavorite) {
            repositoryRealm.putMovieIntoRealm(movie)
        } else {
            repositoryRealm.deleteMovieFromRealm(movie)
        }
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}