package com.garvardinho.kiko.model

class RepositoryImpl : Repository {

    override fun getMoviesFromServer(): List<Movie> {
        return listOf(
            Movie(imageRes = null, year = 1999, month = 4, dayOfMonth = 18),
            Movie(imageRes = null, title = "Wtf man"),
            Movie(imageRes = null, rating = 9.99),
            Movie(imageRes = null, isFavourite = true),
            Movie(imageRes = null),
            Movie(imageRes = null, year = 2016, month = 4, dayOfMonth = 18),
            Movie(imageRes = null),
            Movie(imageRes = null))
    }
}