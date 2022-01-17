package com.garvardinho.kiko.model

class RepositoryImpl : Repository {

    override fun getMoviesFromServer(): List<Movie> {

        return listOf(Movie(image = null, year = 1999, month = 4, dayOfMonth = 18),
            Movie(image = null, title = "Wtf man"),
            Movie(image = null, rating = 9.99),
            Movie(image = null, isFavourite = true),
            Movie(image = null),
            Movie(image = null, year = 2016, month = 4, dayOfMonth = 18),
            Movie(image = null),
            Movie(image = null))
    }
}