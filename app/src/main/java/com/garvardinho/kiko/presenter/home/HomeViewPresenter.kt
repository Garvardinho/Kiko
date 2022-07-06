package com.garvardinho.kiko.presenter.home

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.home.HomeView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter

class HomeViewPresenter(private val router: Router) : MvpPresenter<HomeView>(), HomeViewDelegate {

    private val repository: Repository = RepositoryImpl(RemoteDataSource(), RealmDataSource())
    val nowPlayingCardViewPresenter = NowPlayingCardViewPresenter()
    val upcomingCardViewPresenter = UpcomingCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadNowPlayingMovies()
        loadUpcomingMovies()
    }

    override fun loadNowPlayingMovies() {
        viewState.showNowPlayingLoading(true)
        repository.loadNowPlayingMoviesFromServer()
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
                        nowPlayingCardViewPresenter.setMovies(moviesList.results)
                        viewState.showNowPlayingMovies(moviesList.results)
                    }
                },
                {
                    it.printStackTrace()
                    viewState.showError()
                })
    }

    override fun loadUpcomingMovies() {
        viewState.showUpcomingLoading(true)
        repository.loadUpcomingMoviesFromServer()
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
                    upcomingCardViewPresenter.setMovies(moviesList.results)
                    viewState.showUpcomingMovies(moviesList.results)
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