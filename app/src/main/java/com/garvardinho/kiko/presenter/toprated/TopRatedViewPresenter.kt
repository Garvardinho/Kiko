package com.garvardinho.kiko.presenter.toprated

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RealmDataSource
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.toprated.TopRatedView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedViewPresenter(private val router: Router) : MvpPresenter<TopRatedView>(), TopRatedViewDelegate {

    private val repositoryRemote: Repository = RepositoryImpl(RemoteDataSource())
    private val repositoryRealm: Repository = RepositoryImpl(RealmDataSource())
    val topRatedCardViewPresenter = TopRatedCardViewPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadTopRatedMovies()
    }

    override fun loadTopRatedMovies() {
        viewState.showTopRatedLoading()
        repositoryRemote.loadTopRatedMoviesFromServer(object : Callback<MovieDTO> {
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
                        topRatedCardViewPresenter.setMovies(moviesList)
                        viewState.showTopRatedMovies(moviesList)
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