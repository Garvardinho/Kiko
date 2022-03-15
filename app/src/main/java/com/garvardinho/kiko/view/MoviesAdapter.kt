package com.garvardinho.kiko.view

interface MoviesAdapter {

    fun setOnItemClickListener(onItemClickListener: KOnItemClickListener)
    fun setOnFavoriteClickListener(onFavoriteClickListener: KOnItemClickListener)
}