package com.garvardinho.kiko.model

import androidx.appcompat.widget.AppCompatImageView

data class Movie(
    val title: String = "New movie",
    val image: AppCompatImageView?,
    val year: Int = 2022,
    val month: Int = 10,
    val dayOfMonth: Int = 10,
    val rating: Double = 10.0,
    val isFavourite: Boolean = false
)
