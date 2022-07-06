package com.garvardinho.kiko.presenter.toprated

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.toprated.TopRatedView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter

class TopRatedViewPresenter(private val router: Router) : MvpPresenter<TopRatedView>(), TopRatedViewDelegate {

    private val repository: Repository = RepositoryImpl(RemoteDataSource(), RealmDataSource())
    val topRatedCardViewPresenter = TopRatedCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadTopRatedMovies()
    }

    override fun loadTopRatedMovies() {
        viewState.showTopRatedLoading()
        repository.loadTopRatedMoviesFromServer()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { moviesList ->
                    if (moviesList.results.isEmpty()) {
                        viewState.showError()
                    }
                    else {
                        val favorites = repository.loadFavoriteMoviesFromRealm()
                        for (favoriteMovie in favorites) {
                            moviesList.results.find { movie ->
                                movie.title == favoriteMovie.title
                            }?.isFavorite = true
                        }
                        topRatedCardViewPresenter.setMovies(moviesList.results)
                        viewState.showTopRatedMovies(moviesList.results)
                    }
                },
                {
                    viewState.showError()
                })
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