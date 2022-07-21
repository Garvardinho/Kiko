package com.garvardinho.kiko.presenter.toprated

import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.RealmDataSource
import com.garvardinho.kiko.model.retrofit.MovieDTO
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.toprated.TopRatedView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter
import javax.inject.Inject

class TopRatedViewPresenter : MvpPresenter<TopRatedView>(), TopRatedViewDelegate {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repository: Repository
    val topRatedCardViewPresenter = TopRatedCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadTopRatedMovies()
    }

    override fun loadTopRatedMovies() {
        viewState.showTopRatedLoading()
        repository.loadTopRatedMoviesFromCache()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { cache ->
                if (cache.isEmpty()) {
                    repository.loadTopRatedMoviesFromServer()
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
                                    topRatedCardViewPresenter.setMovies(moviesList.results)
                                    viewState.showTopRatedMovies(moviesList.results)
                                    repository.cacheTopRatedMovies(moviesList.results)
                                }
                            },
                            {
                                viewState.showError()
                            })
                } else {
                    repository.loadTopRatedMoviesFromCache()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            { movieList ->
                                topRatedCardViewPresenter.setMovies(movieList)
                                viewState.showTopRatedMovies(movieList)
                            },
                            {
                                viewState.showError()
                            })
                }
            }
    }

    override fun filterTopRatedMovies(by: Int) {
        topRatedCardViewPresenter.sort(by)
        viewState.filterTopRatedMovies()
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