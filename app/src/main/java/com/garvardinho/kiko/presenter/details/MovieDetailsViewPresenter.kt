package com.garvardinho.kiko.presenter.details

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RealmDataSource
import com.garvardinho.kiko.presenter.ViewDelegate
import com.garvardinho.kiko.view.details.DetailsView
import moxy.MvpPresenter

class MovieDetailsViewPresenter : MvpPresenter<DetailsView>(), ViewDelegate {

    private val repositoryRealm: Repository = RepositoryImpl(RealmDataSource())

    override fun manageFavorite(movie: MovieResultDTO) {
        if (movie.isFavorite) {
            repositoryRealm.putMovieIntoRealm(movie)
        } else {
            repositoryRealm.deleteMovieFromRealm(movie)
        }
    }
}