package com.garvardinho.kiko.model.realm

import com.garvardinho.kiko.model.retrofit.MovieDTO
import io.realm.Realm
import io.realm.kotlin.where

class RealmDataSource : IRealmDataSource {
    private val realm = Realm.getDefaultInstance()

    override fun loadFavoriteMovies(): List<MovieDTO> {
        val resultsRealm = realm.where<RealmMovieDTO>().findAll()
        val results = ArrayList<MovieDTO>()

        for (movie in resultsRealm) {
            results.add(movie.toMovieResultDTO())
        }

        return results
    }

    override fun putMovieIntoRealm(movie: MovieDTO) {
        realm.executeTransaction { transaction ->
            transaction.insert(movie.toManaged())
        }
    }

    override fun deleteMovieFromRealm(movie: MovieDTO) {
        realm.executeTransaction {
            realm.where<RealmMovieDTO>()
                .equalTo("title", movie.title).findFirst()!!.deleteFromRealm()
        }
    }
}