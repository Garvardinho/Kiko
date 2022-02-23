package com.garvardinho.kiko.model.retrofit

import com.garvardinho.kiko.model.MovieDTO
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val API_KEY = "9f9ff549c14dba55067c6fecad30cd71"

class RemoteDataSource {
    private val movieAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .client(createOkHttpClient())
        .build()
        .create(MovieAPI::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun loadNowPlayingMovies(callback: Callback<MovieDTO>) {
        movieAPI.loadNowPlayingMovies(API_KEY, 1).enqueue(callback)
    }

    fun loadUpcomingMovies(callback: Callback<MovieDTO>) {
        movieAPI.loadUpcomingMovies(API_KEY, 1).enqueue(callback)
    }

    fun loadTopRatedMovies(callback: Callback<MovieDTO>) {
        movieAPI.loadTopRatedMovies(API_KEY, 1).enqueue(callback)
    }
}