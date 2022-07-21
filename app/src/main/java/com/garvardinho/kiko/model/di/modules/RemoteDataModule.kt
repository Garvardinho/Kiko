package com.garvardinho.kiko.model.di.modules

import com.garvardinho.kiko.model.retrofit.IRemoteDataSource
import com.garvardinho.kiko.model.retrofit.MovieAPI
import com.garvardinho.kiko.model.retrofit.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class RemoteDataModule {

    @Singleton
    @Binds
    abstract fun remoteDataSource(impl: RemoteDataSource) : IRemoteDataSource

    companion object {
        @Named("baseUrl")
        @Provides
        fun baseUrl(): String = "https://api.themoviedb.org/3/movie/"

        @Named("apiKey")
        @Provides
        fun apiKey(): String = "9f9ff549c14dba55067c6fecad30cd71"

        @Singleton
        @Provides
        fun gson(): Gson = GsonBuilder().setLenient().create()

        @Singleton
        @Provides
        fun movieAPI(
            @Named("baseUrl") baseUrl: String,
            gson: Gson,
        ): MovieAPI = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieAPI::class.java)
    }
}