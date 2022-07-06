package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.MovieResultDTO

interface KikoCardView : KikoItemView {

    fun setImage(url: String)
    fun setFavorite(movie: MovieResultDTO)
    fun setTitle(title: String)
    fun setDate(date: String)
    fun setRating(rating: Double) {}
    fun setOverview(overview: String) {}
}