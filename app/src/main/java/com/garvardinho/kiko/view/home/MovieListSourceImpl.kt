package com.garvardinho.kiko.view.home

import com.garvardinho.kiko.model.Movie

class MovieListSourceImpl(private val dataSource: List<Movie>) : MovieListSource {

    override fun getCardData(position: Int): Movie {
        return dataSource[position]
    }

    override fun getSize(): Int {
        return dataSource.size
    }
}