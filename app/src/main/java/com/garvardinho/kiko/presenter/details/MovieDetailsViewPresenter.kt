package com.garvardinho.kiko.presenter.details

import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.presenter.ViewDelegate
import com.garvardinho.kiko.view.details.DetailsView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class MovieDetailsViewPresenter : MvpPresenter<DetailsView>(), ViewDelegate {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repository: Repository

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