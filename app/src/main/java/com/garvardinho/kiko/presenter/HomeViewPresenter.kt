package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.HomeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewPresenter(private val view: HomeView) : HomeViewDelegate {
    private val repository: Repository = RepositoryImpl(RemoteDataSource())

    override fun loadNowPlayingMovies() {
        view.showNowPlayingLoading()
        repository.loadNowPlayingMoviesFromServer(object : Callback<MovieDTO> {
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.body() == null || response.body()?.results == null) {
                    view.showError()
                } else {
                    response.body()?.results?.let {
                        view.showNowPlayingMovies(it)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                view.showError()
            }
        })
    }

    override fun loadUpcomingMovies() {
        view.showUpcomingLoading()
        repository.loadUpcomingMoviesFromServer(object : Callback<MovieDTO> {
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.body() == null || response.body()?.results == null) {
                    view.showError()
                } else {
                    response.body()?.results?.let {
                        view.showUpcomingMovies(it)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                view.showError()
            }
        })
    }
}