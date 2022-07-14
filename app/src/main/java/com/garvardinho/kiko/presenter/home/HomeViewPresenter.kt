package com.garvardinho.kiko.presenter.home

import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.MovieDTO
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
        repository.loadNowPlayingMoviesFromCache()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { cache ->
                if (cache.isEmpty()) {
                    repository.loadNowPlayingMoviesFromServer()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { moviesList ->
                                if (moviesList.results.isEmpty()) {
                                    viewState.showError()
                                } else {
                                    val favorites = repository.loadFavoriteMoviesFromRealm()
                                    for (favoriteMovie in favorites) {
                                        moviesList.results.find { movie ->
                                            movie.title == favoriteMovie.title
                                        }?.isFavorite = true
                                    }
                                    nowPlayingCardViewPresenter.setMovies(moviesList.results)
                                    viewState.showNowPlayingMovies(moviesList.results)
                                    repository.cacheNowPlayingMovies(moviesList.results)
                                }
                            },
                            {
                                viewState.showError()
                            })
                } else {
                    repository.loadNowPlayingMoviesFromCache()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { movieList ->
                                nowPlayingCardViewPresenter.setMovies(movieList)
                                viewState.showNowPlayingMovies(movieList)
                            },
                            {
                                viewState.showError()
                            })
                }
            }
    }

    override fun loadUpcomingMovies() {
        viewState.showUpcomingLoading(true)
        repository.loadUpcomingMoviesFromCache()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { cache ->
                if (cache.isEmpty()) {
                    repository.loadUpcomingMoviesFromServer()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { moviesList ->
                                if (moviesList.results.isEmpty()) {
                                    viewState.showError()
                                } else {
                                    val favorites = repository.loadFavoriteMoviesFromRealm()
                                    for (favoriteMovie in favorites) {
                                        moviesList.results.find { movie ->
                                            movie.title == favoriteMovie.title
                                        }?.isFavorite = true
                                    }
                                    upcomingCardViewPresenter.setMovies(moviesList.results)
                                    viewState.showUpcomingMovies(moviesList.results)
                                    repository.cacheUpcomingMovies(moviesList.results)
                                }
                            },
                            {
                                viewState.showError()
                            })
                } else {
                    repository.loadUpcomingMoviesFromCache()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { movieList ->
                                upcomingCardViewPresenter.setMovies(movieList)
                                viewState.showUpcomingMovies(movieList)
                            },
                            {
                                viewState.showError()
                            })
                }
            }
    }

    override fun filterNowPlayingMovies(by: Int) {
        nowPlayingCardViewPresenter.sort(by)
        viewState.filterNowPlayingMovies()
    }

    override fun filterUpcomingMovies(by: Int) {
        upcomingCardViewPresenter.sort(by)
        viewState.filterUpcomingMovies()
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