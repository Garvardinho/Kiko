package com.garvardinho.kiko.model.retrofit

import com.garvardinho.kiko.model.MovieResultDTO
import com.garvardinho.kiko.model.MovieResultDTOManaged
import io.realm.Realm
import io.realm.kotlin.where

class RealmDataSource : DataSource {
    private val realm = Realm.getDefaultInstance()

    override fun loadFavoriteMovies(): List<MovieResultDTO> {
        val resultsRealm = realm.where<MovieResultDTOManaged>().findAll()
        val results = ArrayList<MovieResultDTO>()

        for (movie in resultsRealm) {
            results.add(movie.toMovieResultDTO())
        }

        return results
    }

    override fun putMovieIntoRealm(movie: MovieResultDTO) {
        realm.executeTransaction { transaction ->
            transaction.insert(movie.toManaged())
        }
    }

    override fun deleteMovieFromRealm(movie: MovieResultDTO) {
        realm.executeTransaction {
            realm.where<MovieResultDTOManaged>()
                .equalTo("title", movie.title).findFirst()!!.deleteFromRealm()
        }
    }
}