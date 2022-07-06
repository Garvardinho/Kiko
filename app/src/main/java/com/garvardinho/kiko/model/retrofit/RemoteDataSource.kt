package com.garvardinho.kiko.model.retrofit

import com.garvardinho.kiko.model.MovieDTO
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val API_KEY = "9f9ff549c14dba55067c6fecad30cd71"

class RemoteDataSource : RetrofitDataSource {
    private val movieAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build()
        .create(MovieAPI::class.java)

    override fun loadNowPlayingMovies(): Single<MovieDTO> {
        return movieAPI.loadNowPlayingMovies(API_KEY, 1).subscribeOn(Schedulers.io())
    }

    override fun loadUpcomingMovies(): Single<MovieDTO> {
        return movieAPI.loadUpcomingMovies(API_KEY, 1).subscribeOn(Schedulers.io())
    }

    override fun loadTopRatedMovies(): Single<MovieDTO> {
        return movieAPI.loadTopRatedMovies(API_KEY, 1).subscribeOn(Schedulers.io())
    }
}