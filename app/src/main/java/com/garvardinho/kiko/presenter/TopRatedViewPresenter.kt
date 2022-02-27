package com.garvardinho.kiko.presenter

import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.garvardinho.kiko.view.TopRatedView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopRatedViewPresenter(private val view: TopRatedView) : TopRatedViewDelegate {
    private val repository: Repository = RepositoryImpl(RemoteDataSource())

    override fun loadTopRatedMovies() {
        view.showTopRatedLoading()
        repository.loadTopRatedMoviesFromServer(object : Callback<MovieDTO> {
            override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                if (response.body() == null || response.body()?.results == null) {
                    view.showError()
                } else {
                    response.body()?.results?.let {
                        view.showTopRatedMovies(it)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                view.showError()
            }
        })
    }
}