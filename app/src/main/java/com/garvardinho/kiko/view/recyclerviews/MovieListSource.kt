package com.garvardinho.kiko.view.recyclerviews

import com.garvardinho.kiko.model.MovieResultDTO

interface MovieListSource {
    fun getCardData(position: Int): MovieResultDTO
    fun getSize(): Int
}