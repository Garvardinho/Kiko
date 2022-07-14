package com.garvardinho.kiko.model.realm

import com.garvardinho.kiko.model.retrofit.MovieDTO

interface IRealmDataSource {

    fun putMovieIntoRealm(movie: MovieDTO)
    fun deleteMovieFromRealm(movie: MovieDTO)
    fun loadFavoriteMovies(): List<MovieDTO> {
        return listOf()
    }
}