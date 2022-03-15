package com.garvardinho.kiko.presenter.home

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.home.HomeView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewPresenter(private val router: Router) : MvpPresenter<HomeView>(), HomeViewDelegate {

    private val repositoryRemote: Repository = RepositoryImpl(RemoteDataSource())
    private val repositoryRealm: Repository = RepositoryImpl(RealmDataSource())
    val nowPlayingCardViewPresenter = NowPlayingCardViewPresenter()
    val upcomingCardViewPresenter = UpcomingCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadNowPlayingMovies()
        loadUpcomingMovies()
    }

    override fun loadNowPlayingMovies() {
        viewState.showNowPlayingLoading(true)
        repositoryRemote.loadNowPlayingMoviesFromServer(object : Callback<MovieDTO> {
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.body() == null || response.body()?.results == null) {
                    viewState.showError()
                } else {
                    response.body()?.results?.let { moviesList ->
                        val favorites = repositoryRealm.loadFavoriteMoviesFromRealm()
                        for (favoriteMovie in favorites) {
                            moviesList.find { movie ->
                                movie.title == favoriteMovie.title
                            }?.isFavorite = true
                        }
                        nowPlayingCardViewPresenter.setMovies(moviesList)
                        viewState.showNowPlayingMovies(moviesList)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                viewState.showError()
            }
        })
    }

    override fun loadUpcomingMovies() {
        viewState.showUpcomingLoading(true)
        repositoryRemote.loadUpcomingMoviesFromServer(object : Callback<MovieDTO> {
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.body() == null || response.body()?.results == null) {
                    viewState.showError()
                } else {
                    response.body()?.results?.let { moviesList ->
                        val favorites = repositoryRealm.loadFavoriteMoviesFromRealm()
                        for (favoriteMovie in favorites) {
                            moviesList.find { movie ->
                                movie.title == favoriteMovie.title
                            }?.isFavorite = true
                        }
                        upcomingCardViewPresenter.setMovies(moviesList)
                        viewState.showUpcomingMovies(moviesList)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                viewState.showError()
            }
        })
    }

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