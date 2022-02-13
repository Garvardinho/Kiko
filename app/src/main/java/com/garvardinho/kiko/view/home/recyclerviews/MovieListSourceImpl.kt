package com.garvardinho.kiko.view.home.recyclerviews

import com.garvardinho.kiko.model.MovieResultDTO

class MovieListSourceImpl(private val dataSource: List<MovieResultDTO>) : MovieListSource {

    override fun getCardData(position: Int): MovieResultDTO {
        return dataSource[position]
    }

    override fun getSize(): Int {
        return dataSource.size
    }
}