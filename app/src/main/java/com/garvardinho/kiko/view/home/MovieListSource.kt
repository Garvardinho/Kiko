package com.garvardinho.kiko.view.home

import com.garvardinho.kiko.model.Movie

interface MovieListSource {
    fun getCardData(position: Int): Movie
    fun getSize(): Int
}