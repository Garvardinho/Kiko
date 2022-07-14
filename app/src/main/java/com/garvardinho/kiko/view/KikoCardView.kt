package com.garvardinho.kiko.view

import com.garvardinho.kiko.model.retrofit.MovieDTO

interface KikoCardView : KikoItemView {

    fun setImage(url: String)
    fun setFavorite(movie: MovieDTO)
    fun setTitle(title: String)
    fun setDate(date: String)
    fun setRating(rating: Double) {}
    fun setOverview(overview: String) {}
}